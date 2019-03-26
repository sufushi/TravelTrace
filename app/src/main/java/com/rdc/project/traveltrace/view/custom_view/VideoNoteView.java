package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class VideoNoteView extends PLainNoteView implements PLOnPreparedListener {

    private PLVideoTextureView mPLVideoTextureView;
    private boolean mIsStart = false;

    public VideoNoteView(Context context) {
        this(context, null);
    }

    public VideoNoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoNoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createNoteExtendView(Context context) {
        mPLVideoTextureView = new PLVideoTextureView(context);
        mPLVideoTextureView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
        mPLVideoTextureView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
        return mPLVideoTextureView;
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof VideoNote) {
            VideoNote videoNote = (VideoNote) data;
            mPLVideoTextureView.setVideoPath(videoNote.getVideoUrl());
            mPLVideoTextureView.setOnPreparedListener(this);
//            mPLVideoTextureView.start();
//            mIsStart = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPLVideoTextureView != null && !mIsStart) {
            mPLVideoTextureView.requestLayout();
            mPLVideoTextureView.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPLVideoTextureView != null && mIsStart) {
            mPLVideoTextureView.pause();
        }
    }

    @Override
    public void onPrepared(int i) {
        if (!mIsStart) {
            mPLVideoTextureView.start();
        }
    }
}
