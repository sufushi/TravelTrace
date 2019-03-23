package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.User;
import com.rdc.project.traveltrace.view.custom_view.FollowListItemView;

public class FollowListAdapter extends BaseRecyclerViewAdapter<User> {

    private Context mContext;

    public FollowListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = new FollowListItemView(mContext);
        return new FollowListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((FollowListViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class FollowListViewHolder extends BaseRvHolder {

        FollowListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(User user) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(user);
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }
}
