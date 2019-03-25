package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.pili.pldroid.player.widget.PLVideoView;
import com.rdc.project.traveltrace.entity.VideoNote;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class VideoNoteView extends PLainNoteView {

    private PLVideoView mPLVideoView;

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
        mPLVideoView = new PLVideoView(context);
        mPLVideoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(200, context)));
        mPLVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        return mPLVideoView;
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof VideoNote) {
            VideoNote videoNote = (VideoNote) data;
            mPLVideoView.setVideoPath(videoNote.getVideoUrl());
            mPLVideoView.start();
        }
    }
}
