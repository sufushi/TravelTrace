package com.rdc.project.traveltrace.fragment.home_page;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.TimelineContentAdapter;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.entity.TimeLineContent;
import com.rdc.project.traveltrace.view.folding_cell.FoldingCell;
import com.scwang.smartrefresh.header.DropBoxHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimelineFragment extends BasePTRFragment implements OnRefreshListener {

    private FoldingCell mFoldingCell;
    private RecyclerView mRecyclerView;
    private TimelineContentAdapter mAdapter;

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
        mAdapter = new TimelineContentAdapter(getActivity());
        List<TimeLineContent> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new TimeLineContent());
        }
        mAdapter.appendData(list);
    }

    @Override
    protected void initView() {
        mFoldingCell = mRootView.findViewById(R.id.folding_cell);
        mFoldingCell.setOnClickListener(v -> mFoldingCell.toggle(false));
        mRecyclerView = mRootView.findViewById(R.id.cell_content_recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position, View view) {
                mFoldingCell.toggle(false);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
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
