package com.rdc.project.traveltrace.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseDialogFragment;
import com.rdc.project.traveltrace.view.float_background.FloatBackground;
import com.rdc.project.traveltrace.view.float_background.FloatViewFactory;

public class TopDialogFragment extends BaseDialogFragment {

    private FloatBackground mFloatBackground;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatBackground != null) {
            mFloatBackground.endFloat();
        }
    }
}
