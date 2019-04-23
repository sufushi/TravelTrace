package com.rdc.project.traveltrace.fragment.guide_page;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.adapter.ImageFragmentStatePagerAdapter;
import com.rdc.project.traveltrace.adapter.TextFragmentStatePagerAdapter;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.entity.GuidePageText;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.view.guide_page.GuidePageIndicator;
import com.rdc.project.traveltrace.view.guide_page.InnerViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.rdc.project.traveltrace.ui.GuidePageActivity.HAS_SHOW_GUIDE;

public class GuidePageFragment extends BaseFragment implements GuidePageScrollCallback {

    private InnerViewPager mImageViewPager;
    private InnerViewPager mTextViewPager;
    private GuidePageIndicator mGuidePageIndicator;
    private TextView mSkipBtn;

    private int mLastPosition = -1;
    private boolean mFirstPageLock = false;

    public boolean readyToMoveParent = false;

    private ImageFragmentStatePagerAdapter mImageAdapter;
    private TextFragmentStatePagerAdapter mTextAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_guide_page;
    }

    @Override
    protected void initData(Bundle bundle) {
        List<GuidePageBaseImageFragment> fragments = new ArrayList<>();
        fragments.add(new GuidePageFirstImageFragment());
        fragments.add(new GuidePageSecondImageFragment());
        fragments.add(new GuidePageThirdImageFragment());
        mImageAdapter = new ImageFragmentStatePagerAdapter(getChildFragmentManager(), fragments);

        List<GuidePageText> textList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GuidePageText text = new GuidePageText("title" + i, "content" + i);
            textList.add(text);
        }
        mTextAdapter = new TextFragmentStatePagerAdapter(getChildFragmentManager(), textList);
    }

    @Override
    protected void initView() {
        mImageViewPager = mRootView.findViewById(R.id.vp_image);
        mTextViewPager = mRootView.findViewById(R.id.vp_text);
        mGuidePageIndicator = mRootView.findViewById(R.id.view_indicator);
        mSkipBtn = mRootView.findViewById(R.id.tv_skip);

        mImageViewPager.setOffscreenPageLimit(3);
        mImageViewPager.setAdapter(mImageAdapter);
        mImageViewPager.isLockScroll = false;

        mTextViewPager.setAdapter(mTextAdapter);

        mGuidePageIndicator.initIndicator(mTextAdapter.getCount());

        SharePreferenceUtil.put(getActivity(), HAS_SHOW_GUIDE, true);
    }

    @Override
    protected void setListener() {
        mImageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                readyToMoveParent = i == mImageAdapter.getCount() - 1;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mTextViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (mLastPosition < 0) {
                    mLastPosition = 0;
                }
                mGuidePageIndicator.play(mLastPosition, i);
                mLastPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public TextView getSkipBtn() {
        return mSkipBtn;
    }

    @Override
    public void onGuidePageScroll(MotionEvent ev) {
        if (mImageViewPager != null && !mImageViewPager.isLockScroll) {
            mImageViewPager.onTouchEvent(ev);
        }
        if (mTextViewPager != null) {
            mTextViewPager.onTouchEvent(ev);
        }
    }
}
