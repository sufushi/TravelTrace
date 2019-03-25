package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.base.BaseViewHolder;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.view.custom_view.VideoNoteView;
import com.shizhefei.view.multitype.ItemViewProvider;

public class VideoNoteViewProvider extends ItemViewProvider<VideoNote> {

    private Context mContext;

    public VideoNoteViewProvider(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new BaseViewHolder(new VideoNoteView(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, VideoNote videoNote) {
        ((VideoNoteView)viewHolder.itemView).setData(videoNote);
    }
}
