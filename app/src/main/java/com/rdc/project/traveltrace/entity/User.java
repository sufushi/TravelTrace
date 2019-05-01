package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String mUserIcon;
    private String mUserName;
    private String mUserExtraMsg;
    private boolean mHasFollow;

    public User() {
    }

    public String getUserIcon() {
        return mUserIcon;
    }

    public void setUserIcon(String userIcon) {
        mUserIcon = userIcon;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
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

    @Override
    public String toString() {
        return "User{" +
                "mUserIcon='" + mUserIcon + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mUserExtraMsg='" + mUserExtraMsg + '\'' +
                ", mHasFollow=" + mHasFollow +
                '}';
    }
}
