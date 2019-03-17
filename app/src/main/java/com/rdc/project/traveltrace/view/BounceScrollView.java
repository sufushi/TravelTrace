package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.rdc.project.traveltrace.R;

public class BounceScrollView extends ScrollView {

    private View mView;
    private float mY;
    private Rect mRect = new Rect();
    private boolean mIsStartCount = false;
    private boolean mBackgroundIsWhite = false;

    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mView = getChildAt(0);
            super.onFinishInflate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView != null) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if (isNeedReset()) {
                        reset();
                        mIsStartCount = false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float preY = mY;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY);
                    if (!mIsStartCount) {
                        deltaY = 0;
                    }

                    mY = nowY;
                    if (isNeedMove()) {
                        if (mRect.isEmpty()) {
                            mRect.set(mView.getLeft(), mView.getTop(), mView.getRight(), mView.getBottom());
                        }
                        mView.layout(mView.getLeft(), mView.getTop() - deltaY / 2, mView.getRight(), mView.getBottom() - deltaY / 2);
                    }
                    mIsStartCount = true;
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void reset() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, mView.getTop(), mRect.top);
        ta.setDuration(200);
        mView.startAnimation(ta);
        mView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }

    public boolean isNeedReset() {
        return !mRect.isEmpty();
    }

    public boolean isNeedMove() {
        int offset = mView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }
}
