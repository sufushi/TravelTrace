package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rdc.project.traveltrace.adapter.VideoListAdapter;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseHListView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.base.OnClickRecyclerViewListener;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.entity.VideoNoteList;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import java.util.List;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_VIDEO_PATH;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_VIDEO_PREVIEW;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class VideoHListView extends BaseHListView implements IView, OnClickRecyclerViewListener {

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
            mVideoListAdapter.setOnRecyclerViewListener(this);
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

    @Override
    public void onItemClick(int position, View view) {
        if (CollectionUtil.inRange(mVideoListAdapter.getDataList(), position)) {
            VideoNote videoNote = mVideoListAdapter.getDataList().get(position);
            Action action = new Action(ACTION_PRE + ACTION_NAME_VIDEO_PREVIEW + "?" + ACTION_FIELD_VIDEO_PATH + "=" + UriUtil.encode(videoNote.getVideoUrl()));
            ActionManager.doAction(action, getContext());
        }
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
