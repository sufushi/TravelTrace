package com.rdc.project.traveltrace.fragment.dialog_fragment;

import android.view.Gravity;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseDialogFragment;

public abstract class LeftDialogFragment extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.START);
        mWindow.setWindowAnimations(R.style.LeftDialog);
        onLayout();
    }

    protected void onLayout() {
        mWindow.setLayout(mWidth * 2 / 3, mHeight);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .init();
    }

}
