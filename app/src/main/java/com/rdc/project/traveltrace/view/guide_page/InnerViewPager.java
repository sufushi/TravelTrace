package com.rdc.project.traveltrace.view.guide_page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class InnerViewPager extends ViewPager {

    public boolean isLockScroll = false;

    public InnerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public InnerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
