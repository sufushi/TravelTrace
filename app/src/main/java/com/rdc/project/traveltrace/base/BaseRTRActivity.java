package com.rdc.project.traveltrace.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rdc.project.traveltrace.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

public abstract class BaseRTRActivity extends BaseSwipeBackActivity implements com.scwang.smartrefresh.layout.listener.OnRefreshListener, OnLoadMoreListener {

    protected SmartRefreshLayout mRefreshLayout;
    protected RefreshHeader mRefreshHeader;
    protected RefreshFooter mRefreshFooter;
    protected FrameLayout mContainerLayout;

    protected OnRefreshListener mOnRefreshListener;

    @Override
    protected void createContentView() {
        initContentView();
    }

    private void initContentView() {
        setContentView(R.layout.activity_base_ptr);
        initRefreshLayout();
        initContainerLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.activity_layout_refresh);
        mRefreshHeader = createRefreshHeader();
        mRefreshFooter = createRefreshFooter();
        mRefreshLayout.setRefreshHeader(mRefreshHeader);
        mRefreshLayout.setRefreshFooter(mRefreshFooter);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        configRefreshLayout();
        mOnRefreshListener = createRefreshListener();
    }

    private void initContainerLayout() {
        mContainerLayout = (FrameLayout) findViewById(R.id.activity_layout_container);
        View contentView = LayoutInflater.from(this).inflate(getLayoutResID(), mContainerLayout, false);
        mContainerLayout.addView(contentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected abstract RefreshHeader createRefreshHeader();

    protected abstract RefreshFooter createRefreshFooter();

    protected abstract void configRefreshLayout();

    protected abstract OnRefreshListener createRefreshListener();

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onLoadMore();
        }
    }
}
