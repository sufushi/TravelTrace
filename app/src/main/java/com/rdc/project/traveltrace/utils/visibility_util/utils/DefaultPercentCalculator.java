package com.rdc.project.traveltrace.utils.visibility_util.utils;

import android.graphics.Rect;
import android.view.View;

public class DefaultPercentCalculator {

    private final Rect mCurrentViewRect; // 当前视图的方框

    private static class InstanceHolder {
        private static final DefaultPercentCalculator sInstance = new DefaultPercentCalculator();
    }

    private DefaultPercentCalculator() {
        mCurrentViewRect = new Rect();
    }

    public static DefaultPercentCalculator getInstance() {
        return InstanceHolder.sInstance;
    }

    public int getVisibilityPercents(View view) {
        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }

    // 顶部出现
    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    // 底部出现
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

}
