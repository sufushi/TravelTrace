package com.rdc.project.traveltrace.ui;

import android.content.Intent;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseSwipeBackActivity;
import com.rdc.project.traveltrace.view.splash_view.SplashView;
import com.rdc.project.traveltrace.view.splash_view.ThawingView;

import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseSwipeBackActivity {

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
            Intent intent = new Intent();
            if (BmobUser.isLogin()) {
                intent.setClass(SplashActivity.this, HomeActivity.class);
            } else {
                intent.setClass(SplashActivity.this, GuidePageActivity.class);
            }
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        mShimmerFrameLayout.stopShimmerAnimation();
        super.onDestroy();
    }
}
