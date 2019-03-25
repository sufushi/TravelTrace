package com.rdc.project.traveltrace.entity;

public class VideoNote extends PlainNote {

    private String mVideoCoverUrl;
    private String mVideoUrl;
    private long mVideoDuration;

    public VideoNote() {

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
