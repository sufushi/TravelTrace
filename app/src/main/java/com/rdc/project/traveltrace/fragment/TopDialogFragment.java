package com.rdc.project.traveltrace.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseDialogFragment;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.IActionListener;
import com.rdc.project.traveltrace.view.PublishDrawerItemView.DrawerItemData;
import com.rdc.project.traveltrace.view.PublishDrawerView;
import com.rdc.project.traveltrace.view.float_background.FloatBackground;
import com.rdc.project.traveltrace.view.float_background.FloatViewFactory;

import java.util.ArrayList;
import java.util.List;

public class TopDialogFragment extends BaseDialogFragment implements IActionListener {

    private FloatBackground mFloatBackground;
    private PublishDrawerView mPublishDrawerView;

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP);
        mWindow.setWindowAnimations(R.style.TopDialogStyle);
        mWindow.setLayout(mWidth, mHeight * 2 / 5);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_top_dialog;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    @Override
    protected void initView() {
        initFloatBackground();
        initPublishDrawerView();
    }

    private void initFloatBackground() {
        mFloatBackground = mRootView.findViewById(R.id.float_background);
        mFloatBackground.addFloatViewList(FloatViewFactory.createFloatViewList(getActivity()));
        mFloatBackground.post(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int x = mFloatBackground.getMeasuredWidth() / 2;
                int y = 0;
                Animator animator = ViewAnimationUtils.createCircularReveal(mFloatBackground, x, y, 0, mFloatBackground.getMeasuredHeight());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mFloatBackground.setVisibility(View.VISIBLE);
                        mFloatBackground.startFloat();
                    }
                });
                animator.setDuration(800);
                animator.start();
            }
        });
    }

    private void initPublishDrawerView() {
        mPublishDrawerView = mRootView.findViewById(R.id.publish_drawer_view);
        List<DrawerItemData> list = new ArrayList<>();
        list.add(new DrawerItemData(R.drawable.ic_action_article, R.color.orangeRed, "说说", new Action()));
        list.add(new DrawerItemData(R.drawable.ic_action_picture, R.color.colorPrimary, "相册", new Action()));
        list.add(new DrawerItemData(R.drawable.ic_action_video, R.color.seaGreen, "拍摄", new Action()));
        mPublishDrawerView.setDrawerItemList(list, 3);
    }

    @Override
    protected void initListener() {
        mPublishDrawerView.setActionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatBackground != null) {
            mFloatBackground.endFloat();
        }
    }

    @Override
    public void onActionClick(Action action, View view) {
        if (getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }
}
