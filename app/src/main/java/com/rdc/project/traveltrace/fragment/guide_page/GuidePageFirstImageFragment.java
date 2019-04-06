package com.rdc.project.traveltrace.fragment.guide_page;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;

public class GuidePageFirstImageFragment extends GuidePageBaseImageFragment {

    private static final int SCROLL_TO_BOTTOM_BITMAP_HEIGHT = 864;
    private static final int SCROLL_BITMAP_NORMAL_HEIGHT = 1286;
    private static final int SCROLL_BITMAP_SPIT_HEIGHT = 150;

    private int mMarginTopHeight;
    private int mScrollMarginTopHeight;

    private ImageView mIvContent;
    private ImageView mTvBg;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_guide_page_first_image;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
    }

    @Override
    protected void initView() {
        mIvContent = mRootView.findViewById(R.id.iv_scroll);
        mTvBg = mRootView.findViewById(R.id.tv_bg);
        Bitmap bmpShield = BitmapFactory.decodeResource(getResources(), R.drawable.bg_phone);
        Bitmap bmpScroll = BitmapFactory.decodeResource(getResources(), R.drawable.guide_page_image_first);
        mScrollBitmapHeight = SCROLL_BITMAP_NORMAL_HEIGHT;
        mIvContent.setScaleType(ImageView.ScaleType.MATRIX);
        mIvContent.setImageBitmap(bitmapScale(mImageViewWidth, bmpScroll, BITMAP_SCROLL));

        mTvBg.setScaleType(ImageView.ScaleType.MATRIX);
        mTvBg.setImageBitmap(bitmapScale(mImageViewWidth, bmpShield, BITMAP_SHIELD));

        mScrollMarginTopHeight = mNewScrollBitmapHeight * SCROLL_TO_BOTTOM_BITMAP_HEIGHT / mScrollBitmapHeight;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvContent.getLayoutParams();
        mMarginTopHeight = mNewScrollBitmapHeight * SCROLL_BITMAP_SPIT_HEIGHT / SCROLL_BITMAP_NORMAL_HEIGHT - DensityUtil.dp2px(20, getActivity());
        layoutParams.topMargin = mMarginTopHeight;
        mIvContent.setLayoutParams(layoutParams);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void playInAnim() {
        if (mAnimStartY < 0) {
            mAnimStartY = mIvContent.getY();
        }

        if (mObjectAnimator == null) {
            mObjectAnimator = ObjectAnimator.ofFloat(mIvContent, "y", mMarginTopHeight, -mScrollMarginTopHeight + mMarginTopHeight);
        }
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }

        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        mAnimatorSet.play(mObjectAnimator);
        mAnimatorSet.setDuration(3000);
        mAnimatorSet.start();
    }

    @Override
    public void playOutAnim() {
        super.playOutAnim();
    }

    @Override
    public void reset() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
    }
}
