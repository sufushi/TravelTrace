package com.rdc.project.traveltrace;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.fragment.HomeFragment;

public class MainActivity extends BaseRTRActivity {

    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
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

    @Override
    protected String getToolBarTitle() {
        return "home";
    }

    @Override
    protected BasePTRFragment createPTRFragment() {
        return new HomeFragment();
    }

    @Override
    protected void initImmersionBar() {
//        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .navigationBarColor(R.color.gradient1)
                .init();
    }
}
