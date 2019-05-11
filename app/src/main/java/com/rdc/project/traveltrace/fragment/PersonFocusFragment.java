package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.manager.FollowListManager;
import com.rdc.project.traveltrace.view.custom_view.FollowListView;

public class PersonFocusFragment extends BaseBounceFragment {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_focus;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        FollowListView followListView = mRootView.findViewById(R.id.follow_list_view);
        followListView.setData(FollowListManager.getInstance().getFollowList());
    }

    @Override
    protected void setListener() {

    }
}
