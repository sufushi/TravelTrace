package com.rdc.project.traveltrace.view.guide_page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.rdc.project.traveltrace.fragment.guide_page.GuidePageAnimFragment;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageScrollCallback;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageSkipCallback;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class OuterViewPager extends ViewPager {

    public static boolean sIsOtherPageLock = false;

    private boolean mSkipFlag = true;
    private int[] mTvSkipLocation;
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    private GuidePageAnimFragment mGuidePageAnimFragment;
    private TextView mTvSkip;

    private GuidePageSkipCallback mGuidePageSkipCallback;
    private GuidePageScrollCallback mGuidePageScrollCallback;

    public OuterViewPager(@NonNull Context context) {
        this(context, null);
    }

    public OuterViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGuidePageAnimFragment(GuidePageAnimFragment guidePageAnimFragment) {
        mGuidePageAnimFragment = guidePageAnimFragment;
    }

    public void setTvSkip(TextView tvSkip) {
        mTvSkip = tvSkip;
    }

    public void setGuidePageSkipCallback(GuidePageSkipCallback guidePageSkipCallback) {
        mGuidePageSkipCallback = guidePageSkipCallback;
    }

    public void setGuidePageScrollCallback(GuidePageScrollCallback guidePageScrollCallback) {
        mGuidePageScrollCallback = guidePageScrollCallback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (sIsOtherPageLock) {
            requestDisallowInterceptTouchEvent(true);
            return super.onInterceptTouchEvent(ev);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getCurrentItem() == 1 || sIsOtherPageLock) {
            return true;
        }
        if (mGuidePageAnimFragment != null) {
            float x = ev.getX();
            float y = ev.getY();
            if (mTvSkipLocation == null) {
                mTvSkipLocation = new int[2];
                mTvSkip.getLocationOnScreen(mTvSkipLocation);
            }
            if (mLeft == 0) {
                int margin = DensityUtil.dp2px(10, getContext());
                mLeft = mTvSkipLocation[0] - margin;
                mTop = mTvSkipLocation[1] - margin;
                mRight = mTvSkipLocation[0] + mTvSkip.getWidth() + margin;
                mBottom = mTvSkipLocation[1] + mTvSkip.getHeight() + margin;
            }
            if (!(x - mLeft > 0) || !(mRight - x > 0) || !(y - mTop > 0) || !(mBottom - y > 0) || sIsOtherPageLock) {
                mSkipFlag = false;
            }
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (x - mLeft > 0 && mRight - x > 0 && y - mTop > 0 && mBottom - y > 0 && !sIsOtherPageLock && mSkipFlag) {
                    if (mGuidePageSkipCallback != null) {
                        mGuidePageSkipCallback.onGuidePageSkip();
                    }
                }
                mSkipFlag = true;
            }
            if (mGuidePageScrollCallback != null) {
                mGuidePageScrollCallback.onGuidePageScroll();
            }
            if (mGuidePageAnimFragment.readyToMoveParent) {
                return super.onTouchEvent(ev);
            }
        }
        return true;
    }
}
