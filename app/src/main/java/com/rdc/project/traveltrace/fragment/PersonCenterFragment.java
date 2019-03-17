package com.rdc.project.traveltrace.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meg7.widget.CircleImageView;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.ui.PersonDetailActivity;

public class PersonCenterFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView mUserIcon;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_person_center;
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
