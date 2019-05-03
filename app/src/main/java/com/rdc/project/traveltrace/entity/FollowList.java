package com.rdc.project.traveltrace.entity;

import android.view.View;

import com.rdc.project.traveltrace.utils.visibility_util.items.ListItem;
import com.rdc.project.traveltrace.utils.visibility_util.utils.DefaultPercentCalculator;

import java.util.List;

public class FollowList implements ListItem {

    private String userId;
    private List<User> mFollowList;

    public FollowList() {

    }

    public FollowList(List<User> followList) {
        mFollowList = followList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                "userId='" + userId + '\'' +
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
