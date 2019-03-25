package com.rdc.project.traveltrace.entity;

import java.util.List;

public class PictureNote extends PlainNote {

    private List<String> mImgUrls;

    public PictureNote() {

    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    @Override
    public String toString() {
        return "PictureNote{" +
                "mImgUrls=" + mImgUrls +
                ", mUser=" + mUser +
                ", mText='" + mText + '\'' +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }
}
