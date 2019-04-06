package com.rdc.project.traveltrace.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rdc.project.traveltrace.entity.GuidePageText;
import com.rdc.project.traveltrace.fragment.guide_page.GuidePageTextFragment;
import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.List;

import static com.rdc.project.traveltrace.fragment.guide_page.GuidePageTextFragment.ARG_KEY_CONTENT;
import static com.rdc.project.traveltrace.fragment.guide_page.GuidePageTextFragment.ARG_KEY_TITLE;

public class TextFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<GuidePageText> mTexts;

    public TextFragmentStatePagerAdapter(FragmentManager fm, List<GuidePageText> texts) {
        super(fm);
        mTexts = texts;
    }

    @Override
    public Fragment getItem(int position) {
        if (CollectionUtil.inRange(mTexts, position)) {
            GuidePageText guidePageText = mTexts.get(position);
            String title = guidePageText.getTitle();
            String content = guidePageText.getContent();
            Bundle bundle = new Bundle();
            bundle.putString(ARG_KEY_TITLE, title);
            bundle.putString(ARG_KEY_CONTENT, content);
            GuidePageTextFragment fragment = new GuidePageTextFragment();
            fragment.setArguments(bundle);
            return fragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return CollectionUtil.isEmpty(mTexts) ? 0 : mTexts.size();
    }
}
