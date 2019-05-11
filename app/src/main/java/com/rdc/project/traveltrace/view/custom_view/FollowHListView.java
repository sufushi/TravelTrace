package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;

import com.rdc.project.traveltrace.adapter.FollowHListAdapter;
import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.base.BaseHListView;
import com.rdc.project.traveltrace.base.BaseRecyclerViewAdapter;
import com.rdc.project.traveltrace.entity.FollowList;
import com.rdc.project.traveltrace.entity.User;

import java.util.List;

public class FollowHListView extends BaseHListView implements IView {

    private FollowHListAdapter mFollowHListAdapter;

    public FollowHListView(Context context) {
        super(context);
    }

    @Override
    protected BaseRecyclerViewAdapter createAdapter(Context context) {
        mFollowHListAdapter = new FollowHListAdapter(context);
        return mFollowHListAdapter;
    }

    @Override
    public void setData(Object data) {
        if (data instanceof FollowList) {
            List<User> userList = ((FollowList) data).getFollowList();
            mFollowHListAdapter.updateData(userList);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void onActive() {

    }
}
