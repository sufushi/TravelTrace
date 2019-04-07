package com.rdc.project.traveltrace.base;

import android.view.View;

public abstract class BaseBounceActivity extends BaseRTRActivity {

    @Override
    protected boolean isNeedCreateViewStub() {
        return false;
    }

    @Override
    protected void onCreateViewStub(View view) {

    }

    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return true;
    }

}
