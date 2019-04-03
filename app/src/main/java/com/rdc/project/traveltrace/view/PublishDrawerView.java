package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.adapter.PublishDrawerViewAdapter;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.decorator.SpaceGridItemDecoration;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.action.IActionListener;

import java.util.List;

public class PublishDrawerView extends RecyclerView {

    private PublishDrawerViewAdapter mAdapter;
    private IActionListener mActionListener;

    public PublishDrawerView(Context context) {
        this(context, null);
    }

    public PublishDrawerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublishDrawerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.TRANSPARENT);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mAdapter = new PublishDrawerViewAdapter(context);
        mAdapter.setOnRecyclerViewListener(new OnClickRecyclerViewListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (mActionListener != null) {
                    mActionListener.onActionClick(mAdapter.getDataList().get(position).getAction(), view);
                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        setItemAnimator(new DefaultItemAnimator());
        addItemDecoration(new SpaceGridItemDecoration(DensityUtil.dp2px(10, context)));
        setAdapter(mAdapter);
    }

    public void setDrawerItemList(List<PublishDrawerItemView.DrawerItemData> list, int spanCount) {
        setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        mAdapter.updateData(list);
    }

    public void setActionListener(IActionListener actionListener) {
        mActionListener = actionListener;
    }
}
