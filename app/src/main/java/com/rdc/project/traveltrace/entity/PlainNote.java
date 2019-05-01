package com.rdc.project.traveltrace.entity;

public class PlainNote extends Note {

    protected String mText;

    public PlainNote() {

    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public String toString() {
        return "PlainNote{" +
                "mText='" + mText + '\'' +
                ", mUser=" + mUser +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }

}
