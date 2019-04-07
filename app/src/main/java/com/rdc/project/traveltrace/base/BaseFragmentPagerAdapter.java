package com.rdc.project.traveltrace.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void appendFragmentList(List<BaseFragment> list) {
        mFragments = list;
    }

    @Override
    public Fragment getItem(int i) {
        return CollectionUtil.inRange(mFragments, i) ? mFragments.get(i) : null;
    }

    @Override
    public int getCount() {
        return CollectionUtil.isEmpty(mFragments) ? 0 : mFragments.size();
    }
}
