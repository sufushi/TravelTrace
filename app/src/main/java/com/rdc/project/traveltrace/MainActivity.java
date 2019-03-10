package com.rdc.project.traveltrace;

import android.os.AsyncTask;

import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.base.OnRefreshListener;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

public class MainActivity extends BaseRTRActivity implements OnRefreshListener {

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

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected BGARefreshViewHolder createRefreshViewHolder() {
        BGAStickinessRefreshViewHolder viewHolder = new BGAStickinessRefreshViewHolder(this, true);
        viewHolder.setRotateImage(R.mipmap.ic_launcher_round);
        viewHolder.setStickinessColor(R.color.colorPrimary);
        viewHolder.setLoadingMoreText("加载更多...");
        viewHolder.setLoadMoreBackgroundColorRes(R.color.colorPrimary);
        viewHolder.setRefreshViewBackgroundDrawableRes(R.mipmap.ic_launcher);
        viewHolder.setLoadMoreBackgroundDrawableRes(R.mipmap.ic_launcher_round);
        viewHolder.setRefreshViewBackgroundColorRes(R.color.colorAccent);
        return viewHolder;
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    public void onRefreshing() {
        RefreshTask task = new RefreshTask();
        task.execute();
    }

    @Override
    public boolean onLoadMore() {
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
