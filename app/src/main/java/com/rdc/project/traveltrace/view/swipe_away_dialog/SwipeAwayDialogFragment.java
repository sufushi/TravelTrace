package com.rdc.project.traveltrace.view.swipe_away_dialog;

import android.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class SwipeAwayDialogFragment extends DialogFragment {

    private boolean mSwipeable = true;
    private boolean mTiltEnabled = true;
    private boolean mSwipeLayoutGenerated = false;
    private SwipeDismissTouchListener mListener = null;

    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }

    public boolean isSwipeable() {
        return mSwipeable;
    }

    public void setTiltEnabled(boolean tiltEnabled) {
        mTiltEnabled = tiltEnabled;
        if (mListener != null) {
            mListener.setTiltEnabled(tiltEnabled);
        }
    }

    public boolean isTiltEnabled() {
        return mTiltEnabled;
    }

    public boolean onSwipedAway(boolean toRight) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            View content = decorView.getChildAt(0);
            decorView.removeView(content);

            SwipeableFrameLayout layout = new SwipeableFrameLayout(getActivity());
            layout.addView(content);
            decorView.addView(layout);

            mListener = new SwipeDismissTouchListener(decorView, "layout", new SwipeDismissTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Object token) {
                    return isCancelable() && mSwipeable;
                }

                @Override
                public void onDismiss(View view, boolean toRight, Object token) {
                    if (!onSwipedAway(toRight)) {
                        dismiss();
                    }
                }
            });
            mListener.setTiltEnabled(mTiltEnabled);
            layout.setSwipeDismissTouchListener(mListener);
            layout.setOnTouchListener(mListener);
            layout.setClickable(true);
            mSwipeLayoutGenerated = true;
        }
    }

}
