package com.rdc.project.traveltrace.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
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
import java.util.Objects;

public class MomentsFragment extends BasePTRFragment implements OnRefreshListener {

    private MultiTypeAdapter<Object> mMultiTypeAdapter;
    private MultiTypeView mMultiTypeView;
    private List<Object> mList;

    @Override
    protected RefreshHeader createRefreshHeader() {
        DeliveryHeader header = new DeliveryHeader(getActivity());
        header.setBackgroundResource(R.drawable.bg_liner_gradient);
        return header;
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new BallPulseFooter(Objects.requireNonNull(getActivity()))
                .setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(getResources().getColor(R.color.colorPrimary))
                .setNormalColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void configRefreshLayout() {
//        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_moments;
    }

    @Override
    protected void initData(Bundle bundle) {
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new FragmentData(InfoFragment.class, "InfoFragment" + i));
        }
        ItemBinderFactory itemBinderFactory = new ItemBinderFactory(getFragmentManager());
        mMultiTypeAdapter = new MultiTypeAdapter<>(mList, itemBinderFactory);
    }

    @Override
    protected void initView() {
        mMultiTypeView = mRootView.findViewById(R.id.recycler_view_home);
        mMultiTypeView.setAdapter(mMultiTypeAdapter);
    }

    @Override
    protected void setListener() {

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
