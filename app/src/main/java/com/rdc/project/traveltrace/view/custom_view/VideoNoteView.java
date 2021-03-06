package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.entity.PlainNote;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.player.VideoListViewManager;

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
        mVideoCover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onActive();
            }
        });
        mVideoView = new FrameLayout(context);
        mVideoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
        mVideoView.addView(mVideoCover);
        return mVideoView;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof VideoNote) {
            mVideoNote = (VideoNote) data;
            PlainNote plainNote = new PlainNote();
            plainNote.setUser(mVideoNote.getUser());
            plainNote.setText(mVideoNote.getText());
            plainNote.setLikeCount(mVideoNote.getLikeCount());
            plainNote.setCommentCount(mVideoNote.getCommentCount());
            plainNote.setLike(mVideoNote.isLike());
            plainNote.setCreatedAt(mVideoNote.getCreatedAt());
            super.setData(plainNote);
            Glide.with(getContext())
                    .load(mVideoNote.getVideoCoverUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_picture_place_holder))
                    .into(mVideoCover);
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
