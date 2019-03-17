package com.rdc.project.traveltrace.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;

public class PersonDetailActivity extends BaseSwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_person_detail;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return true;
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
