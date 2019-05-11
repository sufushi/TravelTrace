package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.adapter.FollowListAdapter;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.User;

import java.util.List;

public class FollowListView extends RecyclerView implements IView {

    private FollowListAdapter mFollowListAdapter;

    public FollowListView(Context context) {
        this(context, null);
    }

    public FollowListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FollowListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOverScrollMode(OVER_SCROLL_NEVER);
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mFollowListAdapter = new FollowListAdapter(context);
        setAdapter(mFollowListAdapter);
        setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setData(Object data) {
        if (data instanceof FollowList) {
            List<User> userList = ((FollowList) data).getFollowList();
            mFollowListAdapter.updateData(userList);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void onActive() {

    }
}
