package com.rdc.project.traveltrace.view.folding_cell;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class HeightAnimation extends Animation {

    private final View mView;
    private final int mHeightFrom;
    private final int mHeightTo;

    public HeightAnimation(View view, int heightFrom, int heightTo, int duration) {
        this.mView = view;
        this.mHeightFrom = heightFrom;
        this.mHeightTo = heightTo;
        this.setDuration(duration);
    }

    public HeightAnimation withInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            this.setInterpolator(interpolator);
        }
        return this;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float newHeight = mHeightFrom + (mHeightTo - mHeightFrom) * interpolatedTime;

        if (interpolatedTime == 1) {
            mView.getLayoutParams().height = mHeightTo;
        } else {
            mView.getLayoutParams().height = (int) newHeight;
        }
        mView.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    public boolean isFillEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "HeightAnimation{" +
                "mHeightFrom=" + mHeightFrom +
                ", mHeightTo=" + mHeightTo +
                ", offset =" + getStartOffset() +
                ", duration =" + getDuration() +
                '}';
    }

}
