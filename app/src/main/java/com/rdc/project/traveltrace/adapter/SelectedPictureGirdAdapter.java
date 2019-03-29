package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.Picture;
import com.rdc.project.traveltrace.view.custom_view.SelectedPictureGridItemView;

public class SelectedPictureGirdAdapter extends BaseRecyclerViewAdapter<Picture> {

    private Context mContext;

    public SelectedPictureGirdAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelectedPictureViewHolder(new SelectedPictureGridItemView(mContext));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((SelectedPictureViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class SelectedPictureViewHolder extends BaseRvHolder {

        SelectedPictureViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Picture picture) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(picture);
            }
        }
    }
}
