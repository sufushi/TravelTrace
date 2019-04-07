package com.rdc.project.traveltrace.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;
import com.rdc.project.traveltrace.fragment.LoginFragment;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageFragment;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageScrollCallback;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageSkipCallback;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.view.guide_page.OuterViewPager;

public class GuidePageActivity extends BaseSwipeBackActivity {

    private static final int FRAGMENT_GUIDE_PAGE = 0;
    private static final int FRAGMENT_LOGIN_PAGE = 1;
    private static final int FRAGMENT_PAGE_COUNT = 2;

    private ImageView mIvLogo;
    private OuterViewPager mOuterViewPager;

    private float mLogoY;
    private AnimatorSet mAnimatorSet;

    private GuidePageFragment mGuidePageFragment;

    private GuidePageFragmentStatePagerAdapter mAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {
        mAdapter = new GuidePageFragmentStatePagerAdapter(getSupportFragmentManager());
    }

    @Override
    protected void initView() {
        mIvLogo = (ImageView) findViewById(R.id.iv_logo);
        mOuterViewPager = (OuterViewPager) findViewById(R.id.vp_parent);
        mOuterViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mOuterViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == FRAGMENT_LOGIN_PAGE) {
                    OuterViewPager.sIsOtherPageLock = true;
                    mIvLogo.postDelayed(() -> {
                        if (mLogoY == 0) {
                            mLogoY = mIvLogo.getY();
                        }
                        playLogoInAnim();
                    }, 500);
                    mOuterViewPager.postDelayed(() -> {

                    }, 300);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void playLogoInAnim() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mIvLogo, "scaleX", 1.0f, 0.5f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mIvLogo, "scaleY", 1.0f, 0.5f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(mIvLogo, "y", mLogoY, DensityUtil.dp2px(15, GuidePageActivity.this));

        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim1).with(anim2);
        mAnimatorSet.play(anim2).with(anim3);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    private class GuidePageFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public GuidePageFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_GUIDE_PAGE:
                    mGuidePageFragment = new GuidePageFragment();
                    mOuterViewPager.setGuidePageFragment(mGuidePageFragment);
                    mOuterViewPager.setGuidePageSkipCallback(() -> mOuterViewPager.setCurrentItem(FRAGMENT_LOGIN_PAGE));
                    mOuterViewPager.setGuidePageScrollCallback(mGuidePageFragment);
                    return mGuidePageFragment;
                case FRAGMENT_LOGIN_PAGE:
                    return new LoginFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_PAGE_COUNT;
        }
    }

}
