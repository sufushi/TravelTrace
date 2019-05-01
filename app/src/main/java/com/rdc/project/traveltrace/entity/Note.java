package com.rdc.project.traveltrace.entity;

import android.view.View;

import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;
import com.rdc.project.traveltrace.utils.visibility_util.utils.DefaultPercentCalculator;

import cn.bmob.v3.BmobObject;

public class Note extends BmobObject implements ListItem  {

    protected User mUser;
    protected int mCommentCount;
    protected int mLikeCount;
    protected boolean mIsLike;

    public Note() {

    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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
                ", mCommentCount=" + mCommentCount +
                ", mLikeCount=" + mLikeCount +
                ", mIsLike=" + mIsLike +
                '}';
    }

    @Override
    public int getVisibilityPercents(View view) {
        return DefaultPercentCalculator.getInstance().getVisibilityPercents(view);
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {

    }

    @Override
    public void deactivate(View currentView, int position) {

    }
}
