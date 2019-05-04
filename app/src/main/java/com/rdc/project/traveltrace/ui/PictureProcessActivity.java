package com.rdc.project.traveltrace.ui;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PictureProcessFragment;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class PictureProcessActivity extends BaseBounceActivity {

    private PictureProcessFragment mPictureProcessFragment;

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_puzzle_filter);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        mPictureProcessFragment = new PictureProcessFragment();
        return mPictureProcessFragment;
    }

    @Override
    protected void initImmersionBar() {
        findViewById(R.id.tool_bar).setBackgroundResource(R.color.black);
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .statusBarColor(R.color.black)
                .navigationBarColor(R.color.black)
                .init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        int size = DensityUtil.dp2px(24, this);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_complete);
        drawable.setBounds(0, 0, size, size);
        mActionBtn.setCompoundDrawables(drawable, null, null, null);
        mActionBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPictureProcessFragment != null) {
                    mPictureProcessFragment.onActionBtnClick();
                }
            }
        });
    }
}
