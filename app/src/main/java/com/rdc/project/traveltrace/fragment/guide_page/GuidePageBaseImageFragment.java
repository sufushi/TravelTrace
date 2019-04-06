package com.rdc.project.traveltrace.fragment.guide_page;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import com.rdc.project.traveltrace.base.BaseFragment;
import com.rdc.project.traveltrace.utils.DensityUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;

public abstract class GuidePageBaseImageFragment extends BaseFragment {

    public final int BITMAP_SCROLL = 11;
    public final int BITMAP_SHIELD = 12;

    public float mAnimStartY = -1;
    public int mScrollBitmapHeight = 0;
    public int mNewScrollBitmapHeight = 0;
    public int mImageViewWidth = 0;

    private float mScaleWidth = 0;
    private float mScaleHeight = 0;

    protected AnimatorSet mAnimatorSet;
    protected ObjectAnimator mObjectAnimator;

    @Override
    protected void initData(Bundle bundle) {
        int parentViewWidth = MeasureUtil.getScreenWidth(getActivity()) - DensityUtil.dp2px(40, getActivity());
        mImageViewWidth = parentViewWidth - DensityUtil.dp2px(60, getActivity());
    }

    public Bitmap bitmapScale(int ivWidth, Bitmap bitmap, int type) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newHeight = (int) ((float) height) * ivWidth / width;

        if (type == BITMAP_SCROLL) {
            mNewScrollBitmapHeight = newHeight;
        }

        mScaleWidth = ((float) ivWidth) / width;
        mScaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public Bitmap bitmapScale(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public void playInAnim() {

    }

    public void playOutAnim() {

    }

    public void reset() {

    }

}
