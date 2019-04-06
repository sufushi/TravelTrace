package com.rdc.project.traveltrace.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rdc.project.traveltrace.fragment.guide_page.GuidePageBaseImageFragment;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

public class ImageFragmentStatePagerAdapter extends FragmentPagerAdapter {

    private List<GuidePageBaseImageFragment> mFragments;

    public ImageFragmentStatePagerAdapter(FragmentManager fm, List<GuidePageBaseImageFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return CollectionUtil.inRange(mFragments, position) ? mFragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return CollectionUtil.isEmpty(mFragments) ? 0 : mFragments.size();
    }
}
