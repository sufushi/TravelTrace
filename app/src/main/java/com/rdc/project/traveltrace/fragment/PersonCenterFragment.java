package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.Objects;

public class PersonCenterFragment extends BasePTRFragment implements OnRefreshListener {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected RefreshHeader createRefreshHeader() {

        return new ClassicsHeader(Objects.requireNonNull(getActivity()))
                .setAccentColor(getResources().getColor(R.color.white))
                .setEnableLastTime(false)
                .setLastUpdateText("")
                .setProgressResource(0)
                .setArrowResource(0);
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new ClassicsFooter(Objects.requireNonNull(getActivity()));
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_center;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.finishLoadMore(0);
    }
}
