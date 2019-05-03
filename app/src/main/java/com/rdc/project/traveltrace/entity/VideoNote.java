package com.rdc.project.traveltrace.entity;

import android.util.Log;
import android.view.View;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.utils.visibility_util.utils.DefaultPercentCalculator;

public class VideoNote extends Note {

    private User mUser;
    private String mVideoCoverUrl;
    private String mVideoUrl;
    private long mVideoDuration;
    private String mText;

    public VideoNote() {

    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public int getVisibilityPercents(View view) {
        return DefaultPercentCalculator.getInstance().getVisibilityPercents(view);
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.i("VideoNote", "onActive");
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
                ", mCommentCount=" + getCommentCount() +
                ", mLikeCount=" + getLikeCount() +
                ", mIsLike=" + isLike() +
                '}';
    }
}
