package com.rdc.project.traveltrace.base;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;

public abstract class BaseRTRActivity extends BaseSwipeBackActivity {

    protected Toolbar mToolbar;

    @Override
    protected void createContentView() {
        initContentView();
    }

    private void initContentView() {
        setContentView(R.layout.activity_base_ptr);
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initToolBar();
        initContainerLayout();
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }

    protected boolean isImmersionBarEnabled() {
        return true;
    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getToolBarTitle());
    }

    private void initContainerLayout() {
        BasePTRFragment fragment = createPTRFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_layout_container, fragment);
            fragmentTransaction.commit();
        }
    }

    protected abstract String getToolBarTitle();

    protected abstract BasePTRFragment createPTRFragment();

    @Override
    protected void onResume() {
        super.onResume();
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).init();
        }
    }
}
