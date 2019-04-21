package com.rdc.project.traveltrace.ui;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceActivity;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.fragment.PersonVideoListFragment;

public class PersonVideoListActivity extends BaseBounceActivity {

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.string_person_video_list);
    }

    @Override
    protected BaseFragment createPTRFragment() {
        return new PersonVideoListFragment();
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
