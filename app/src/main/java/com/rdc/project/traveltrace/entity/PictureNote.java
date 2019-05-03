package com.rdc.project.traveltrace.entity;

import android.util.Log;
import android.view.View;

import java.util.List;

public class PictureNote extends Note {

    private User mUser;
    private List<String> mImgUrls;
    private String mText;

    public PictureNote() {

    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.i("PictureNote", "onActive");
        super.setActive(newActiveView, newActiveViewPosition);
    }

    @Override
    public String toString() {
        return "PictureNote{" +
                "mImgUrls=" + mImgUrls +
                ", mUser=" + mUser +
                ", mText='" + mText + '\'' +
                ", mCommentCount=" + getCommentCount() +
                ", mLikeCount=" + getLikeCount() +
                ", mIsLike=" + isLike() +
                '}';
    }
}
