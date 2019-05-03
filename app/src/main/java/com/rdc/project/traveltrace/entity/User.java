package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

public class User extends BmobUser {

    private String mUserIcon;
    private String mBackgroundUrl;
    private String mUserExtraMsg;
    private boolean mHasFollow;
    private BmobRelation mFollowUserList;
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

    public BmobRelation getFollowUserList() {
        return mFollowUserList;
    }

    public void setFollowUserList(BmobRelation followUserList) {
        mFollowUserList = followUserList;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserIcon='" + mUserIcon + '\'' +
                ", mBackgroundUrl='" + mBackgroundUrl + '\'' +
                ", mUserExtraMsg='" + mUserExtraMsg + '\'' +
                ", mHasFollow=" + mHasFollow +
                ", mFollowUserList=" + mFollowUserList +
                '}';
    }
}
