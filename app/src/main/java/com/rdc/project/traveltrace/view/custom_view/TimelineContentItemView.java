package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.view.timeline_view.TimelineView;

public class TimelineContentItemView extends LinearLayout implements IView {

    private int mViewType;
    private TimelineView mTimelineView;

    public TimelineContentItemView(Context context) {
        this(context, null);
    }

    public TimelineContentItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimelineContentItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_timeline_content, this);
        setOrientation(HORIZONTAL);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTimelineView = findViewById(R.id.timeline_view);
    }

    public void setViewType(int viewType) {
        mViewType = viewType;
        mTimelineView.initLine(mViewType);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onActive() {

    }
}
