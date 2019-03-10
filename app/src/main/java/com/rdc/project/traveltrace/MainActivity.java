package com.rdc.project.traveltrace;

import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;

public class MainActivity extends BaseSwipeBackActivity {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
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
}
