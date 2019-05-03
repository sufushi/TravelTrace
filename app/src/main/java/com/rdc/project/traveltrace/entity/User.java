package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String mUserIcon;
    private String mBackgroundUrl;
    private String mUserExtraMsg;
    private boolean mHasFollow;
    private FollowList mFollowList;

    public User() {
    }

    public String getUserIcon() {
        return mUserIcon;
    }

    public void setUserIcon(String userIcon) {
        mUserIcon = userIcon;
    }

    public String getBackgroundUrl() {
        return mBackgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        mBackgroundUrl = backgroundUrl;
    }

    public String getUserExtraMsg() {
        return mUserExtraMsg;
    }

    public void setUserExtraMsg(String userExtraMsg) {
        mUserExtraMsg = userExtraMsg;
    }

    public boolean isHasFollow() {
        return mHasFollow;
    }

    public void setHasFollow(boolean hasFollow) {
        mHasFollow = hasFollow;
    }

    public FollowList getFollowList() {
        return mFollowList;
    }

    public void setFollowList(FollowList followList) {
        mFollowList = followList;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserIcon='" + mUserIcon + '\'' +
                ", mBackgroundUrl='" + mBackgroundUrl + '\'' +
                ", mUserExtraMsg='" + mUserExtraMsg + '\'' +
                ", mHasFollow=" + mHasFollow +
                ", mFollowList=" + mFollowList +
                '}';
    }
}
