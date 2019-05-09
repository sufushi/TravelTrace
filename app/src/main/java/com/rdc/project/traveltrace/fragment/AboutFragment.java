package com.rdc.project.traveltrace.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseBounceFragment;
import com.rdc.project.traveltrace.utils.UriUtil;
import com.rdc.project.traveltrace.utils.action.Action;
import com.rdc.project.traveltrace.utils.action.ActionManager;

import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_FIELD_URL;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_NAME_H5;
import static com.rdc.project.traveltrace.utils.action.ActionConstant.ACTION_PRE;

public class AboutFragment extends BaseBounceFragment implements View.OnClickListener {

    private static final String PAGE_ABOUT_AUTHOR = "https://github.com/sufushi";

    private LinearLayout mPersonAboutLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        mPersonAboutLayout = mRootView.findViewById(R.id.ll_about_author);
    }

    @Override
    protected void setListener() {
        mPersonAboutLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_about_author:
                Action action = new Action(ACTION_PRE + ACTION_NAME_H5 + "?" + ACTION_FIELD_URL + "=" + UriUtil.encode(PAGE_ABOUT_AUTHOR));
                ActionManager.doAction(action, getActivity());
                break;
            default:
                break;
        }
    }
}
