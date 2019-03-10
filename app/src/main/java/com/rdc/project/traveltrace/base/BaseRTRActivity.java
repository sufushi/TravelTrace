package com.rdc.project.traveltrace.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rdc.project.traveltrace.R;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public abstract class BaseRTRActivity extends BaseSwipeBackActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    protected BGARefreshLayout mRefreshLayout;
    protected BGARefreshViewHolder mBGARefreshViewHolder;
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
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.activity_layout_refresh);
        mBGARefreshViewHolder = createRefreshViewHolder();
        mRefreshLayout.setRefreshViewHolder(mBGARefreshViewHolder);
        mRefreshLayout.setDelegate(this);
        mOnRefreshListener = createRefreshListener();
    }

    private void initContainerLayout() {
        mContainerLayout = (FrameLayout) findViewById(R.id.activity_layout_container);
        View contentView = LayoutInflater.from(this).inflate(getLayoutResID(), mContainerLayout, false);
        mContainerLayout.addView(contentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected abstract BGARefreshViewHolder createRefreshViewHolder();

    protected abstract OnRefreshListener createRefreshListener();

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        if (mOnRefreshListener != null) {
            return mOnRefreshListener.onLoadMore();
        }
        return false;
    }
}
