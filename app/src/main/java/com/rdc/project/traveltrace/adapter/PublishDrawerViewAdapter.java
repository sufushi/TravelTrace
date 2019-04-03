package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.utils.CircularRevealViewUtil;
import com.rdc.project.traveltrace.view.PublishDrawerItemView;

public class PublishDrawerViewAdapter extends BaseRecyclerViewAdapter<PublishDrawerItemView.DrawerItemData> {

    private Context mContext;

    public PublishDrawerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = new PublishDrawerItemView(mContext);
        view.setVisibility(View.INVISIBLE);
        return new PublishDrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((PublishDrawerViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class PublishDrawerViewHolder extends BaseRvHolder {

        PublishDrawerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(PublishDrawerItemView.DrawerItemData drawerItemData) {
            if (itemView instanceof PublishDrawerItemView) {
                PublishDrawerItemView view = (PublishDrawerItemView) itemView;
                view.setDrawerItemData(drawerItemData);
                CircularRevealViewUtil.doAnim(view, 1000);
            }
        }
    }
}
