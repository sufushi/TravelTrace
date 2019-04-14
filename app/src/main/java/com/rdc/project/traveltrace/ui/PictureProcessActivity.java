package com.rdc.project.traveltrace.ui;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PictureProcessFragment;

public class PictureProcessActivity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return "编辑";
    }

    @Override
    protected BaseFragment createPTRFragment() {
        return new PictureProcessFragment();
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
