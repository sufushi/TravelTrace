package com.rdc.project.traveltrace.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseSwipeBackActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSwipeBackConfig();
        initData();
        initView();
        initListener();
    }

    private void setSwipeBackConfig() {
        boolean enableSwipeBack = isEnableSwipeBack();
        if (enableSwipeBack) {
            setSwipeBackEnable(true);
            SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
            swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        } else {
            setSwipeBackEnable(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutResID();

    protected abstract boolean isEnableSwipeBack();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initListener();
}
