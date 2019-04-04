package com.rdc.project.traveltrace.view.splash_view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.rdc.project.traveltrace.R;

public class ThawingView extends View {
    private Bitmap mSplashBitmap;
    private Bitmap mDstBitmap;
    private float mAlpha;
    private float mScale;
    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private Paint mPaint;
    private long mDuration = 2000;

    public ThawingView(Context context) {
        this(context, null);
    }

    public ThawingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mDstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_shade);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mSplashBitmap != null) {
            canvas.drawBitmap(mSplashBitmap, 0, 0, null);
            canvas.scale(mScale, mScale, mDstBitmap.getWidth() / 2.0f, mDstBitmap.getHeight() / 2.0f);
            mPaint.setXfermode(mMode);
            canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);
        }
        setAlpha(mAlpha);
    }

    public void setSplashBitmap(Bitmap bitmap) {
        mSplashBitmap = bitmap;
        invalidate();
    }

    public void startAnimate(Bitmap bitmap) {
        setSplashBitmap(bitmap);
        getAlphaValueAnimator().start();
        getScaleValueAnimator().start();
    }

    @NonNull
    private ValueAnimator getScaleValueAnimator() {
        ValueAnimator scaleVa = ValueAnimator.ofFloat(0, 6);
        scaleVa.addUpdateListener(valueAnimator -> mScale = (float) valueAnimator.getAnimatedValue());
        scaleVa.setDuration(mDuration);
        return scaleVa;
    }

    @NonNull
    private ValueAnimator getAlphaValueAnimator() {
        ValueAnimator alphaVa = ValueAnimator.ofFloat(1, 0f);
        alphaVa.addUpdateListener(valueAnimator -> {
            mAlpha = (float) valueAnimator.getAnimatedValue();
            postInvalidateDelayed(16);
        });

        alphaVa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        alphaVa.setDuration(mDuration);
        return alphaVa;
    }
}
