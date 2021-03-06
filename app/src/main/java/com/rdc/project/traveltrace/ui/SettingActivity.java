package com.rdc.project.traveltrace.ui;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.SettingFragment;

public class SettingActivity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_setting);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        return new SettingFragment();
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
