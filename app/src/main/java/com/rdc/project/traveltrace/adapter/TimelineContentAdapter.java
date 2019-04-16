package com.rdc.project.traveltrace.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.TimeLineContent;
import com.rdc.project.traveltrace.view.custom_view.TimelineContentItemView;
import com.rdc.project.traveltrace.view.timeline_view.TimelineView;

public class TimelineContentAdapter extends BaseRecyclerViewAdapter<TimeLineContent> {

    private Context mContext;

    public TimelineContentAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        TimelineContentItemView view = new TimelineContentItemView(mContext);
        view.setViewType(viewType);
        return new TimelineContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((TimelineContentViewHolder) viewHolder).bindView(mDataList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    private class TimelineContentViewHolder extends BaseRvHolder {

        TimelineContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(TimeLineContent timeLineContent) {
            if (itemView instanceof IView) {
                ((IView) itemView).setData(timeLineContent);
            }
        }
    }
}
