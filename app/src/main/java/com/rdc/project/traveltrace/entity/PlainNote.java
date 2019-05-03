package com.rdc.project.traveltrace.entity;

public class PlainNote extends Note {

    private User mUser;
    private String mText;

    public PlainNote() {

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

    @Override
    public String toString() {
        return "PlainNote{" +
                "mText='" + mText + '\'' +
                ", mUser=" + getUser() +
                ", mCommentCount=" + getCommentCount() +
                ", mLikeCount=" + getLikeCount() +
                ", mIsLike=" + isLike() +
                '}';
    }

}
