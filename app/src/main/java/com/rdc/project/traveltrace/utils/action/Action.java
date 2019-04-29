package com.rdc.project.traveltrace.utils.action;

public class Action {

    private String mActionUrl;
    private int mTransition;

    public Action() {

    }

    public Action(String actionUrl) {
        this(actionUrl, 0);
    }

    public Action(String actionUrl, int transition) {
        mActionUrl = actionUrl;
        mTransition = transition;
    }

    public String getActionUrl() {
        return mActionUrl;
    }

    public void setActionUrl(String actionUrl) {
        mActionUrl = actionUrl;
    }

    public int getTransition() {
        return mTransition;
    }

    public void setTransition(int transition) {
        mTransition = transition;
    }

    @Override
    public String toString() {
        return "Action{" +
                "mActionUrl='" + mActionUrl + '\'' +
                ", mTransition=" + mTransition +
                '}';
    }
}
