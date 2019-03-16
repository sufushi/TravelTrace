package com.rdc.project.traveltrace;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.fragment.MomentsFragment;
import com.rdc.project.traveltrace.fragment.PersonCenterFragment;
import com.rdc.project.traveltrace.fragment.TimelineFragment;

public class HomeActivity extends BaseRTRActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final int NAV_TAB_MOMENTS = 0;
    private static final int NAV_TAB_TIMELINE = 1;
    private static final int NAV_TAB_PERSON_CENTER = 2;

    private MomentsFragment mMomentsFragment;
    private TimelineFragment mTimelineFragment;
    private PersonCenterFragment mPersonCenterFragment;
    private BasePTRFragment mCurrentFragment = null;
    private FragmentManager mFragmentManager;

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
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getToolBarTitle() {
        return "home";
    }

    @Override
    protected BasePTRFragment createPTRFragment() {
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
        BottomNavigationBar bottomNavigationBar = view.findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_moments, getString(R.string.string_moments)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_timeline, getString(R.string.string_timeline)))
                .addItem(new BottomNavigationItem(R.drawable.ic_nav_person_center, getString(R.string.string_person_center)))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .navigationBarColor(R.color.gradient1)
                .init();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (position) {
            case NAV_TAB_MOMENTS:
                if (mMomentsFragment == null) {
                    mMomentsFragment = new MomentsFragment();
                }
                fragmentTransaction.show(mMomentsFragment);
                mCurrentFragment = mMomentsFragment;
                break;
            case NAV_TAB_TIMELINE:
                if (mTimelineFragment == null) {
                    mTimelineFragment = new TimelineFragment();
                }
                fragmentTransaction.show(mTimelineFragment);
                mCurrentFragment = mTimelineFragment;
                break;
            case NAV_TAB_PERSON_CENTER:
                if (mPersonCenterFragment == null) {
                    mPersonCenterFragment = new PersonCenterFragment();
                }
                fragmentTransaction.show(mPersonCenterFragment);
                mCurrentFragment = mPersonCenterFragment;
                break;
            default:
                break;
        }
        updateContainerLayout();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void updateContainerLayout() {
        super.updateContainerLayout();

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
}
