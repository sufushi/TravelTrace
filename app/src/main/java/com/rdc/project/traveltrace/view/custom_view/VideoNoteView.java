package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.VideoListViewManager;

public class VideoNoteView extends PLainNoteView {

    private ImageView mVideoCover;
    private FrameLayout mVideoView;

    private VideoListViewManager mVideoListViewManager;
    private VideoNote mVideoNote;

    public VideoNoteView(Context context) {
        this(context, null);
    }

    public VideoNoteView(VideoListViewManager manager, Context context) {
        this(context, null);
        mVideoListViewManager = manager;
    }

    public VideoNoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createNoteExtendView(Context context) {
        mVideoCover = new ImageView(context);
        mVideoCover.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mVideoCover.setScaleType(ImageView.ScaleType.FIT_XY);
        mVideoCover.setImageResource(R.drawable.ic_picture_place_holder);
        mVideoView = new FrameLayout(context);
        mVideoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
        mVideoView.addView(mVideoCover);
        return mVideoView;
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof VideoNote) {
            mVideoNote = (VideoNote) data;
        }
    }

    @Override
    public void onActive() {
        String url = mVideoNote.getVideoUrl();
        if (!TextUtils.isEmpty(url) && mVideoListViewManager != null) {
            mVideoListViewManager.attach(mVideoView, url);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
