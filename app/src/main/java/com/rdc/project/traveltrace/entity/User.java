package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String mUserNikName;
    private String mUserIcon;
    private String mBackgroundUrl;
    private String mUserExtraMsg;
    private boolean mSex;

    public User() {

    }

    public String getUserNikName() {
        return mUserNikName;
    }

    public void setUserNikName(String userNikName) {
        mUserNikName = userNikName;
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

    public boolean isSex() {
        return mSex;
    }

    public void setSex(boolean sex) {
        mSex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserNikName='" + mUserNikName + '\'' +
                ", mUserIcon='" + mUserIcon + '\'' +
                ", mBackgroundUrl='" + mBackgroundUrl + '\'' +
                ", mUserExtraMsg='" + mUserExtraMsg + '\'' +
                ", mSex=" + mSex +
                '}';
    }
}
