package com.rdc.project.traveltrace.entity;

import java.util.List;

public class FollowList {

    private List<User> mFollowList;

    public FollowList() {

    }

    public FollowList(List<User> followList) {
        mFollowList = followList;
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
                "mFollowList=" + mFollowList +
                '}';
    }
}
