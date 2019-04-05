package com.rdc.project.traveltrace.view.swipe_away_dialog;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class SwipeableFrameLayout extends FrameLayout {

    private SwipeDismissTouchListener mTouchListener;

    public SwipeableFrameLayout(Context context) {
        super(context);
    }

    public void setSwipeDismissTouchListener(SwipeDismissTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mTouchListener != null) {
            if (mTouchListener.onTouch(this, ev)) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
