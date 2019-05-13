package com.rdc.project.traveltrace.entity;

import cn.bmob.v3.BmobObject;

public class UpdateInfo extends BmobObject {

    private String mApkUrl;
    private String mVersionName;
    private int mVersionCode;

    public UpdateInfo() {

    }

    public String getApkUrl() {
        return mApkUrl;
    }

    public void setApkUrl(String apkUrl) {
        mApkUrl = apkUrl;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "mApkUrl='" + mApkUrl + '\'' +
                ", mVersionName='" + mVersionName + '\'' +
                ", mVersionCode='" + mVersionCode + '\'' +
                '}';
    }
}
