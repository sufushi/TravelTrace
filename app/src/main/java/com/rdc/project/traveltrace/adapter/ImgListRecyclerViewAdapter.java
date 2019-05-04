package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.view.SquareImageView;

public class ImgListRecyclerViewAdapter extends BaseRecyclerViewAdapter<String> {

    private Context mContext;

    public ImgListRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SquareImageView imageView = new SquareImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new ImgListHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ImgListHolder) viewHolder).bindView(mDataList.get(i));
    }

    private class ImgListHolder extends BaseRvHolder {

        ImgListHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(String s) {
            Glide.with(mContext).load(s).into((ImageView) itemView);
        }
    }
}
