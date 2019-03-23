package com.rdc.project.traveltrace.entity;

import java.util.List;

public class Note {

    private User mUser;
    private String mText;
    private List<String> mImgUrls;
    private int mCommentCount;
    private int mLikeCount;
    private boolean mIsLike;

    public Note() {
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public int getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        mLikeCount = likeCount;
    }

    public boolean isLike() {
        return mIsLike;
    }

    public void setLike(boolean like) {
        mIsLike = like;
    }

    @Override
    public String toString() {
        return "Note{" +
                "mUser=" + mUser +
                ", mText='" + mText + '\'' +
                ", mImgUrls=" + mImgUrls +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }
}
