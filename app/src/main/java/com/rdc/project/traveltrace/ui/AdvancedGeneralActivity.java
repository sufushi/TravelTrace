package com.rdc.project.traveltrace.ui;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.AdvancedGeneralFragment;

public class AdvancedGeneralActivity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_pattern_recognition);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        AdvancedGeneralFragment fragment = new AdvancedGeneralFragment();
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
