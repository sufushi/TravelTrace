package com.rdc.project.traveltrace.entity;

import android.view.View;

import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;

public class PlainNote implements ListItem {

    protected User mUser;
    protected String mText;
    protected int mCommentCount;
    protected int mLikeCount;
    protected boolean mIsLike;

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
        return "PlainNote{" +
                "mUser=" + mUser +
                ", mText='" + mText + '\'' +
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }

    @Override
    public int getVisibilityPercents(View view) {
        return 0;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {

    }

    @Override
    public void deactivate(View currentView, int position) {

    }
}
