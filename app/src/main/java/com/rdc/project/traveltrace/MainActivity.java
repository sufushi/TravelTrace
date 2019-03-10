package com.rdc.project.traveltrace;

import android.os.AsyncTask;

import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

public class MainActivity extends BaseSwipeBackActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(this, true);
        viewHolder.setLoadingMoreText("加载更多...");
        viewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
        viewHolder.setLoadMoreBackgroundDrawableRes(R.mipmap.ic_launcher_round);
        viewHolder.setRefreshViewBackgroundColorRes(R.color.colorAccent);
        viewHolder.setRefreshViewBackgroundDrawableRes(R.mipmap.ic_launcher);
        mRefreshLayout.setRefreshViewHolder(viewHolder);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        RefreshTask task = new RefreshTask();
        task.execute();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        LoadMoreTask task = new LoadMoreTask();
        task.execute();
        return true;
    }

    private class RefreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mRefreshLayout != null) {
                mRefreshLayout.endRefreshing();
            }
        }

    }

    private class LoadMoreTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mRefreshLayout != null) {
                mRefreshLayout.endLoadingMore();
            }
        }
    }

}
