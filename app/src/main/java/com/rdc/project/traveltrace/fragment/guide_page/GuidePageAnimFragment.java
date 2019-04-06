package com.rdc.project.traveltrace.fragment.guide_page;

import android.os.Bundle;
import android.widget.TextView;

import com.rdc.project.traveltrace.adapter.ImageFragmentStatePagerAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.view.guide_page.GuidePageIndicator;
import com.rdc.project.traveltrace.view.guide_page.InnerViewPager;

public class GuidePageAnimFragment extends BaseFragment {

    private InnerViewPager mImageViewPager;
    private InnerViewPager mTextViewPager;
    private GuidePageIndicator mGuidePageIndicator;
    private TextView mSkipBtn;

    private int mLastPosition = -1;
    private boolean mFirstPageLock = false;

    public boolean readyToMoveParent = false;

    private ImageFragmentStatePagerAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }
}
