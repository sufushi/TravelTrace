package com.rdc.project.traveltrace;

import android.os.AsyncTask;

import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

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
    protected RefreshHeader createRefreshHeader() {
        return new DeliveryHeader(this);
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate);
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    public void onRefresh() {
        RefreshTask task = new RefreshTask();
        task.execute();
    }

    @Override
    public void onLoadMore() {
        LoadMoreTask task = new LoadMoreTask();
        task.execute();
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
                mRefreshLayout.finishRefresh();
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
                mRefreshLayout.finishLoadMore();
            }
        }
    }

}
