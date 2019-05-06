package com.rdc.project.traveltrace.ui;

import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.H5Fragment;

public class H5Activity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return "详情";
    }

    @Override
    protected BaseFragment createPTRFragment() {
        H5Fragment fragment = new H5Fragment();
        fragment.setArguments(getIntent().getBundleExtra("bundle"));
        return fragment;
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
