package com.rdc.project.traveltrace.ui;

import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.base.CommonSwipeAwayDialogFragment;
import com.rdc.project.traveltrace.fragment.dialog_fragment.PublishDrawerDialogFragment;
import com.rdc.project.traveltrace.fragment.home_page.MomentsFragment;
import com.rdc.project.traveltrace.fragment.home_page.PersonCenterFragment;
import com.rdc.project.traveltrace.fragment.home_page.TimelineFragment;
import com.rdc.project.traveltrace.utils.HandlerUtil;
import com.rdc.project.traveltrace.view.pop_menu.PopMenu;
import com.rdc.project.traveltrace.view.pop_menu.PopMenuItem;
import com.rdc.project.traveltrace.view.toast.CommonToast;
import com.yw.game.floatmenu.FloatItem;
import com.yw.game.floatmenu.FloatLogoMenu;
import com.yw.game.floatmenu.FloatMenuView;

import java.util.ArrayList;

public class HomeActivity extends BaseRTRActivity implements BottomNavigationBar.OnTabSelectedListener, HandlerUtil.OnReceiveMessageListener {

    private static final int NAV_TAB_MOMENTS = 0;
    private static final int NAV_TAB_TIMELINE = 1;
    private static final int NAV_TAB_PERSON_CENTER = 2;

    private static final int MSG_BACK_CLICK = 0x11;
    private static final int BACK_EXIT_INTERVAL = 2000;

    private MomentsFragment mMomentsFragment;
    private TimelineFragment mTimelineFragment;
    private PersonCenterFragment mPersonCenterFragment;
    private FragmentManager mFragmentManager;

    private PopMenu mPopMenu;
    private FloatLogoMenu mFloatLogoMenu;

    private static boolean sIsReady = false;

    @Override
    protected int getLayoutResID() {
        return R.layout.layout_view_stub_home;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        HandlerUtil.getInstance().register(this);
    }

    @Override
    protected void initView() {
        initPopMenu();
        initFloatMenu();
        mToolbar.post(() -> mContainer.setPadding(0, mToolbar.getHeight(), 0, 0));
    }

    private void initPopMenu() {
        mPopMenu = new PopMenu.Builder().attachToActivity(this)
                .addMenuItem(new PopMenuItem("说说", getResources().getDrawable(R.drawable.ic_action_article)))
                .addMenuItem(new PopMenuItem("相册", getResources().getDrawable(R.drawable.ic_action_picture)))
                .addMenuItem(new PopMenuItem("拍摄", getResources().getDrawable(R.drawable.ic_action_video)))
                .setOnItemClickListener((popMenu, position) -> {

                })
                .build();
        mPopMenu.setOnMenuCloseListener(v -> {

        });
    }

    private void initFloatMenu() {
        ArrayList<FloatItem> itemList = new ArrayList<>();
        itemList.add(new FloatItem("发表", getResources().getColor(R.color.white), getResources().getColor(R.color.transparent), BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_publish)));
        itemList.add(new FloatItem("记录", getResources().getColor(R.color.white), getResources().getColor(R.color.transparent), BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_record)));
        mFloatLogoMenu = new FloatLogoMenu.Builder()
                .withActivity(this)
                .logo(BitmapFactory.decodeResource(getResources(), R.drawable.ic_sun))
                .drawCicleMenuBg(true)
                .backMenuColor(0xbbf5f28f)
                .setBgDrawable(this.getResources().getDrawable(R.drawable.bg_float_menu))
                .defaultLocation(FloatLogoMenu.RIGHT)
                .drawRedPointNum(false)
                .setFloatItems(itemList)
                .showWithListener(new FloatMenuView.OnMenuClickListener() {
                    @Override
                    public void onItemClick(int pos, String s) {
//                        Intent intent = new Intent(HomeActivity.this, PublishPictureNoteActivity.class);
//                        startActivity(intent);
                        if (pos == 0) {
                            PublishDrawerDialogFragment publishDrawerDialogFragment = new PublishDrawerDialogFragment();
                            publishDrawerDialogFragment.show(getSupportFragmentManager(), PublishDrawerDialogFragment.class.getSimpleName());
                        } else {
                            CommonSwipeAwayDialogFragment.newInstance(CommonSwipeAwayDialogFragment.Type.APPCOMPAT).show(getSupportFragmentManager(), "alert");
                        }
                    }

                    @Override
                    public void dismiss() {

                    }
                });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getToolBarTitle() {
        return getResources().getString(R.string.string_moments);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        if (mCurrentFragment == null) {
            mCurrentFragment = new MomentsFragment();
        }
        return mCurrentFragment;
    }

    @Override
    protected boolean isNeedCreateViewStub() {
        return true;
    }

    @Override
    protected void onCreateViewStub(View view) {
        final BottomNavigationBar bottomNavigationBar = view.findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_moments, getString(R.string.string_moments)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_timeline, getString(R.string.string_timeline)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_person_center, getString(R.string.string_person_center)))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .navigationBarColor(R.color.gradient1)
                .init();
    }

    @Override
    protected boolean needBackIcon() {
        return false;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        int titleRes = R.string.app_name;
        switch (position) {
            case NAV_TAB_MOMENTS:
                if (mMomentsFragment == null) {
                    mMomentsFragment = new MomentsFragment();
                }
                mCurrentFragment = mMomentsFragment;
                titleRes = R.string.string_moments;
                break;
            case NAV_TAB_TIMELINE:
                if (mTimelineFragment == null) {
                    mTimelineFragment = new TimelineFragment();
                }
                mCurrentFragment = mTimelineFragment;
                titleRes = R.string.string_timeline;
                break;
            case NAV_TAB_PERSON_CENTER:
                if (mPersonCenterFragment == null) {
                    mPersonCenterFragment = new PersonCenterFragment();
                }
                mCurrentFragment = mPersonCenterFragment;
                titleRes = R.string.string_person_center;
                break;
            default:
                break;
        }
        mToolbar.setTitle(titleRes);
        updateContainerLayout();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mMomentsFragment != null) {
            fragmentTransaction.hide(mMomentsFragment);
        }
        if (mTimelineFragment != null) {
            fragmentTransaction.hide(mTimelineFragment);
        }
        if (mPersonCenterFragment != null) {
            fragmentTransaction.hide(mPersonCenterFragment);
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == MSG_BACK_CLICK) {
            sIsReady = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!sIsReady) {
                sIsReady = true;
                CommonToast.info(this, "再按一次退出APP", CommonToast.LENGTH_SHORT).show();
                HandlerUtil.getInstance().getHandler().sendEmptyMessageDelayed(MSG_BACK_CLICK, BACK_EXIT_INTERVAL);
            } else {
                finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
