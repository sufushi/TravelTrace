package com.rdc.project.traveltrace.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rdc.project.traveltrace.R;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public abstract class BasePTRFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    protected BGARefreshLayout mRefreshLayout;
    protected BGARefreshViewHolder mBGARefreshViewHolder;
    protected FrameLayout mContainerLayout;

    protected OnRefreshListener mOnRefreshListener;

    @Override
    protected void onCreateContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_base_ptr, container, false);
        initRefreshLayout();
        initContainerLayout();
    }

    private void initContainerLayout() {
        mContainerLayout = mRootView.findViewById(R.id.activity_layout_container);
        View contentView = LayoutInflater.from(getContext()).inflate(getLayoutResourceId(), mContainerLayout, false);
        mContainerLayout.addView(contentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initRefreshLayout() {
        mRefreshLayout = mRootView.findViewById(R.id.fragment_layout_refresh);
        mBGARefreshViewHolder = createRefreshViewHolder();
        mRefreshLayout.setRefreshViewHolder(mBGARefreshViewHolder);
        mRefreshLayout.setDelegate(this);
        mOnRefreshListener = createRefreshListener();
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
