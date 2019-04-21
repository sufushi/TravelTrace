package com.rdc.project.traveltrace.base;

import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;

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

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.tool_bar)
                .navigationBarColor(R.color.gradient1)
                .init();
    }
}
