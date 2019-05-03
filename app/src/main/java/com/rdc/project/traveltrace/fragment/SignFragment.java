package com.rdc.project.traveltrace.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.base.BaseFragmentPagerAdapter;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;

import java.util.ArrayList;
import java.util.List;

public class SignFragment extends BaseFragment {

    private static final int SIGN_PAGE_LOGIN = 0;
    private static final int SIGN_PAGE_REGISTER = 1;

    private RelativeLayout mSignLayout;
    private TextView mTvLogin;
    private TextView mTvRegister;
    private ViewPager mViewPager;

    private BaseFragmentPagerAdapter mAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_sign;
    }

    @Override
    protected void initData(Bundle bundle) {
        mAdapter = new BaseFragmentPagerAdapter(getFragmentManager());
        List<BaseFragment> list = new ArrayList<>();
        list.add(new LoginFragment());
        list.add(new RegisterFragment());
        mAdapter.appendFragmentList(list);
    }

    @Override
    protected void initView() {
        mSignLayout = mRootView.findViewById(R.id.sign_layout);
        mTvLogin = mRootView.findViewById(R.id.tv_login);
        mTvRegister = mRootView.findViewById(R.id.tv_register);
        mViewPager = mRootView.findViewById(R.id.view_pager_sign);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(SIGN_PAGE_LOGIN);
            }
        });
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(SIGN_PAGE_REGISTER);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == SIGN_PAGE_LOGIN) {
                    mTvLogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_triangle_indicator);
                    mTvRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mTvLogin.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mTvRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_triangle_indicator);
                }
            }
        });
    }

    public void playInAnim() {
        mSignLayout.setVisibility(View.VISIBLE);
        AnimatorSet mAnimatorSet;
        ObjectAnimator anim = ObjectAnimator.ofFloat(mSignLayout, "y", MeasureUtil.getScreenHeight(getActivity()), DensityUtil.dp2px(160, getActivity()));

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }
}
