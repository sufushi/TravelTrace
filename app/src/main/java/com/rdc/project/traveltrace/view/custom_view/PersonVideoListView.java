package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.VideoNoteList;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.TipsView;

public class PersonVideoListView extends LinearLayout implements IView {

    private TipsView mTipsView;
    private VideoHListView mVideoHListView;

    public PersonVideoListView(Context context) {
        this(context, null);
    }

    public PersonVideoListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonVideoListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_person_video_list, this);
        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dp2px(10, context);
        setPadding(padding, padding, padding, padding);

        initViews();
    }

    private void initViews() {
        mTipsView = findViewById(R.id.tips_view);
        mVideoHListView = findViewById(R.id.video_list_view);

        mTipsView.showEmptyView(R.drawable.bg_empty_video, getResources().getString(R.string.string_video_list_empty_tips));
    }

    @Override
    public void setData(Object data) {
        if (data instanceof VideoNoteList) {
            VideoNoteList videoHListView = (VideoNoteList) data;
            mVideoHListView.setData(videoHListView);
            mVideoHListView.setVisibility(VISIBLE);
            mTipsView.setVisibility(GONE);
        } else {
            mVideoHListView.setVisibility(GONE);
            mTipsView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onActive() {

    }
}
