package com.rdc.project.traveltrace.base;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.gyf.barlibrary.ImmersionBar;
import com.rdc.project.traveltrace.R;

public abstract class BaseRTRActivity extends BaseSwipeBackActivity {

    protected Toolbar mToolbar;
    protected ViewGroup mContainer;
    protected ViewStub mViewStub;
    protected ViewGroup mRoot;

    @Override
    protected void createContentView() {
        initContentView();
    }

    private void initContentView() {
        setContentView(R.layout.activity_base_ptr);
        initRoot();
        initContainerLayout();
        initToolBar();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (isNeedCreateViewStub()) {
            initViewStub();
        }
    }

    private void initRoot() {
        mRoot = (ViewGroup) findViewById(R.id.activity_root);
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
        mContainer = (ViewGroup) findViewById(R.id.activity_layout_container);
        BaseFragment fragment = createPTRFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (fragment.isAdded() && fragment.isHidden()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.replace(R.id.activity_layout_container, fragment);
            }
            fragmentTransaction.commit();
        }
    }

    private void initViewStub() {
        mViewStub = (ViewStub) findViewById(R.id.activity_view_stub);
        mViewStub.setLayoutResource(getLayoutResID());
        View view = mViewStub.inflate();
        mViewStub.setVisibility(View.VISIBLE);
        onCreateViewStub(view);
    }

    protected abstract String getToolBarTitle();

    protected abstract BaseFragment createPTRFragment();

    protected abstract boolean isNeedCreateViewStub();

    protected abstract void onCreateViewStub(View view);

    protected void updateContainerLayout() {
        initContainerLayout();
    }

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
