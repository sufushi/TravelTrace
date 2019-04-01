package com.rdc.project.traveltrace.fragment;

import android.view.Gravity;

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
        mWindow.setLayout(mWidth, mHeight / 3);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_top_dialog;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
//                .titleBar(R.id.toolbar)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    @Override
    protected void initView() {
        mFloatBackground = mRootView.findViewById(R.id.float_background);
        mFloatBackground.addFloatViewList(FloatViewFactory.createFloatViewList(getActivity()));
        mFloatBackground.post(() -> mFloatBackground.startFloat());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatBackground != null) {
            mFloatBackground.endFloat();
        }
//        getActivity().getActivityImmersionBar().keyboardEnable(true).init();
    }

}
