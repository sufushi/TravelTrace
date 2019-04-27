package com.rdc.project.traveltrace.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;
import com.rdc.project.traveltrace.utils.SharePreferenceUtil;
import com.rdc.project.traveltrace.view.splash_view.SplashView;
import com.rdc.project.traveltrace.view.splash_view.ThawingView;

import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseSwipeBackActivity {

    private static final String LAST_SPLASH_TIME = "last_splash_time";
    private static final long SHOW_SPLASH_INTERVAL = 60 * 1000L;

    private SplashView mSplashView;
    private ThawingView mThawingView;
    private ShimmerFrameLayout mShimmerFrameLayout;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {
        long last_splash_time = (long) SharePreferenceUtil.get(this, LAST_SPLASH_TIME, 0L);
        long current_time = System.currentTimeMillis();
        SharePreferenceUtil.put(this, LAST_SPLASH_TIME, current_time);
        Log.i("error", "interval=" + (current_time - last_splash_time));
        if (current_time - last_splash_time < SHOW_SPLASH_INTERVAL) {
            jumpToHome();
        }
    }

    @Override
    protected void initView() {
        mSplashView = (SplashView) findViewById(R.id.splash_view);
        mThawingView = (ThawingView) findViewById(R.id.thawing_view);
        mShimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.splash_shimmer);
        mSplashView.startAnimate();
        mShimmerFrameLayout.useDefaults();
        mShimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    protected void initListener() {
        mSplashView.setOnEndListener(splashView -> {
            mSplashView.setVisibility(View.GONE);
            mThawingView.setVisibility(View.VISIBLE);
            mThawingView.startAnimate(splashView.getDrawingCache());
        });
        mThawingView.setOnEndListener(thawingView -> {
            jumpToHome();
        });
    }

    private void jumpToHome() {
        Intent intent = new Intent();
        if (BmobUser.isLogin()) {
            intent.setClass(SplashActivity.this, HomeActivity.class);
        } else {
            intent.setClass(SplashActivity.this, GuidePageActivity.class);
        }
        mIsJumpOut = true;
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mShimmerFrameLayout != null) {
            mShimmerFrameLayout.stopShimmerAnimation();
        }
        super.onDestroy();
    }
}
