package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.view.folding_cell.FoldingCell;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.Objects;

public class TimelineFragment extends BasePTRFragment implements OnRefreshListener {

    @Override
    protected RefreshHeader createRefreshHeader() {
        return new DropBoxHeader(Objects.requireNonNull(getActivity()));
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
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return this;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        final FoldingCell foldingCell = mRootView.findViewById(R.id.folding_cell);
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.finishRefresh(2000);
    }

    @Override
    public void onLoadMore() {
        mRefreshLayout.finishLoadMore(2000);
    }
}
