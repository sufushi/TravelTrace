package com.rdc.project.traveltrace.fragment.guide_page;

import android.os.Bundle;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;

public class GuidePageTextFragment extends BaseFragment {

    public static final String ARG_KEY_TITLE = "title";
    public static final String ARG_KEY_CONTENT = "content";

    private String mTitle;
    private String mContent;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_guide_page_text;
    }

    @Override
    protected void initData(Bundle bundle) {
        if (bundle != null) {
            String title = bundle.getString(ARG_KEY_TITLE);
            String content = bundle.getString(ARG_KEY_CONTENT);
            mTitle = title == null ? "" : title;
            mContent = content == null ? "" : content;
        }
    }

    @Override
    protected void initView() {
        TextView tvTitle = mRootView.findViewById(R.id.tv_title);
        TextView tvContent = mRootView.findViewById(R.id.tv_content);
        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
    }

    @Override
    protected void setListener() {

    }
}
