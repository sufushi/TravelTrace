package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.adapter.VideoListAdapter;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseHListView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.entity.VideoNoteList;

import java.util.List;

public class VideoHListView extends BaseHListView implements IView {

    private VideoListAdapter mVideoListAdapter;

    public VideoHListView(Context context) {
        this(context, null);
    }

    public VideoHListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoHListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setData(Object data) {
        if (data instanceof VideoNoteList) {
            List<VideoNote> videoNoteList = ((VideoNoteList) data).getVideoNoteList();
            mVideoListAdapter.updateData(videoNoteList);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void onActive() {

    }

    @Override
    protected BaseRecyclerViewAdapter createAdapter(Context context) {
        mVideoListAdapter = new VideoListAdapter(context);
        return mVideoListAdapter;
    }
}
