package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.view.custom_view.VideoHListItemView;

public class VideoListAdapter extends BaseRecyclerViewAdapter<VideoNote> {

    private Context mContext;

    public VideoListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = new VideoHListItemView(mContext);
        return new PersonVideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((PersonVideoListViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    private class PersonVideoListViewHolder extends BaseRvHolder {

        PersonVideoListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(VideoNote videoNote) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(videoNote);
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
            }
        }
    }
}
