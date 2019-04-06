package com.rdc.project.traveltrace.fragment.home_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BasePTRFragment;
import com.rdc.project.traveltrace.base.OnRefreshListener;
import com.rdc.project.traveltrace.ui.PersonDetailActivity;
import com.rdc.project.traveltrace.view.EmptyViewFooter;
import com.rdc.project.traveltrace.view.EmptyViewHeader;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

public class PersonCenterFragment extends BasePTRFragment implements View.OnClickListener {

    private CircleImageView mUserIcon;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_center;
    }

    @Override
    protected RefreshHeader createRefreshHeader() {
        return new EmptyViewHeader(getActivity());
    }

    @Override
    protected RefreshFooter createRefreshFooter() {
        return new EmptyViewFooter(mRefreshLayout, getActivity());
    }

    @Override
    protected void configRefreshLayout() {
        mRefreshLayout.setDragRate(0.8f);
    }

    @Override
    protected OnRefreshListener createRefreshListener() {
        return null;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mUserIcon = mRootView.findViewById(R.id.user_icon);
        mUserIcon.setOnClickListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                if (getActivity() == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonDetailActivity.class);
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), mUserIcon, "share_img");
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
