package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.view.RectangleImageView;

public class VideoListAdapter extends BaseRecyclerViewAdapter<VideoNote> {

    private Context mContext;

    public VideoListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RectangleImageView imageView = new RectangleImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new VideoItemHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoItemHolder) viewHolder).bindView(mDataList.get(i));
    }

    private class VideoItemHolder extends BaseRvHolder {

        VideoItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(VideoNote videoNote) {
            Glide.with(mContext).load(videoNote.getVideoCoverUrl()).into((RectangleImageView) itemView);
        }
    }
}
