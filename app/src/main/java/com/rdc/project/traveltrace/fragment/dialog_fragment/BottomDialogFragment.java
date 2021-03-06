package com.rdc.project.traveltrace.fragment.dialog_fragment;

import android.view.Gravity;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseDialogFragment;

public abstract class BottomDialogFragment extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
        onLayout();
    }

    protected void onLayout() {
        mWindow.setLayout(mWidth, mHeight / 2);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .navigationBarWithKitkatEnable(false)
                .init();
    }
}
