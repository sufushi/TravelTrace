package com.rdc.project.traveltrace.entity;

import java.util.List;

public class Note {

    private String mText;
    private List<String> mImgUrls;
    private int mCommentCount;
    private int mLikeCount;

    public Note() {
    }

    public Note(String text, List<String> imgUrls, int commentCount, int likeCount) {
        mText = text;
        mImgUrls = imgUrls;
        mCommentCount = commentCount;
        mLikeCount = likeCount;
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

    @Override
    public String toString() {
        return "Note{" +
                "mText='" + mText + '\'' +
                ", mImgUrls=" + mImgUrls +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                '}';
    }
}
