package com.rdc.project.traveltrace.ui;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PicturePuzzleFragment;

public class PicturePuzzleActivity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_puzzle);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        return new PicturePuzzleFragment();
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

    }

    @Override
    protected void initListener() {

    }
}
