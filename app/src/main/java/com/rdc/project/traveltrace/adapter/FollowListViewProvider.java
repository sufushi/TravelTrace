package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseViewHolder;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.view.custom_view.FollowHListView;
import com.shizhefei.view.multitype.ItemViewProvider;

public class FollowListViewProvider extends ItemViewProvider<FollowList> {

    private Context mContext;

    public FollowListViewProvider(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new BaseViewHolder(new FollowHListView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, FollowList followList) {
        ((FollowHListView)viewHolder.itemView).setData(followList);
    }
}
