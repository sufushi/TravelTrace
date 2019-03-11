package com.rdc.project.traveltrace;

import android.os.AsyncTask;

import com.rdc.project.traveltrace.base.BaseRTRActivity;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.fragment.InfoFragment;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.shizhefei.view.multitype.ItemBinderFactory;
import com.shizhefei.view.multitype.MultiTypeAdapter;
import com.shizhefei.view.multitype.MultiTypeView;
import com.shizhefei.view.multitype.provider.FragmentData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseRTRActivity implements OnRefreshListener {

    private MultiTypeAdapter<Object> mMultiTypeAdapter;
    private MultiTypeView mMultiTypeView;
    private List<Object> mList;

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
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new FragmentData(InfoFragment.class, "InfoFragment" + i));
        }
        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getSupportFragmentManager());
        mMultiTypeAdapter = new MultiTypeAdapter<>(mList, itemBinderFactory);
    }

    @Override
    protected void initView() {
        mMultiTypeView = (MultiTypeView) findViewById(R.id.recycler_view_main);
        mMultiTypeView.setAdapter(mMultiTypeAdapter);
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
