package com.rdc.project.traveltrace.fragment;

import android.view.Gravity;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseDialogFragment;

public class TopDialogFragment extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP);
        mWindow.setWindowAnimations(R.style.TopDialogStyle);
        mWindow.setLayout(mWidth, mHeight / 2);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_top_dialog;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .titleBar(R.id.toolbar)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().getActivityImmersionBar().keyboardEnable(true).init();
    }

}
