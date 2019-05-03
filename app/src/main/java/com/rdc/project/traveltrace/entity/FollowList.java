package com.rdc.project.traveltrace.entity;

import android.view.View;

import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;
import com.rdc.project.traveltrace.utils.visibility_util.utils.DefaultPercentCalculator;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class FollowList extends BmobObject implements ListItem {

    private String mUserId;
    private List<User> mFollowList;

    public FollowList() {

    }

    public FollowList(List<User> followList) {
        mFollowList = followList;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public List<User> getFollowList() {
        return mFollowList;
    }

    public void setFollowList(List<User> followList) {
        mFollowList = followList;
    }

    @Override
    public String toString() {
        return "FollowList{" +
                "mUserId='" + mUserId + '\'' +
                ", mFollowList=" + mFollowList +
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
