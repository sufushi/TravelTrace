package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.view.EmptyViewFooter;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

public class PicturePuzzleFragment extends BasePTRFragment {

    @Override
    protected RefreshHeader createRefreshHeader() {
        return new EmptyViewHeader(getActivity());
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new EmptyViewFooter(mRefreshLayout, getActivity());
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return null;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_picture_puzzle;
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
}
