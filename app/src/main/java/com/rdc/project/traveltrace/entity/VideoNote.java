package com.rdc.project.traveltrace.entity;

import android.graphics.Rect;
import android.view.View;

import com.rdc.project.traveltrace.arch.view.IView;

public class VideoNote extends PlainNote {

    private String mVideoCoverUrl;
    private String mVideoUrl;
    private long mVideoDuration;

    private final Rect mCurrentViewRect; // 当前视图的方框

    public VideoNote() {
        mCurrentViewRect = new Rect();
    }

    public String getVideoCoverUrl() {
        return mVideoCoverUrl;
    }

    public void setVideoCoverUrl(String videoCoverUrl) {
        mVideoCoverUrl = videoCoverUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public long getVideoDuration() {
        return mVideoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        mVideoDuration = videoDuration;
    }

    @Override
    public int getVisibilityPercents(View view) {
        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }

    // 顶部出现
    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    // 底部出现
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        super.setActive(newActiveView, newActiveViewPosition);
        if (newActiveView instanceof IView) {
            ((IView) newActiveView).onActive();
        }
    }

    @Override
    public void deactivate(View currentView, int position) {
        super.deactivate(currentView, position);
    }

    @Override
    public String toString() {
        return "VideoNote{" +
                "mVideoCoverUrl='" + mVideoCoverUrl + '\'' +
                ", mVideoUrl='" + mVideoUrl + '\'' +
                ", mVideoDuration=" + mVideoDuration +
                ", mUser=" + mUser +
                ", mText='" + mText + '\'' +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }
}
