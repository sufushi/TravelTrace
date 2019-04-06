package com.rdc.project.traveltrace.view.guide_page;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;

import java.util.ArrayList;

public class GuidePageIndicator extends LinearLayout {

    private Context mContext;
    private ArrayList<ImageView> mImageViews;

    private int mHeightSelect;
    private Bitmap mBmpSelect;
    private Bitmap mBmpNormal;

    private AnimatorSet mOutAnimatorSet;
    private AnimatorSet mInAnimatorSet;

    public GuidePageIndicator(Context context) {
        this(context, null);
    }

    public GuidePageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuidePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOrientation(HORIZONTAL);

        mHeightSelect = DensityUtil.dp2px(24, context);
        mBmpSelect = BitmapFactory.decodeResource(getResources(), R.drawable.guide_page_indicator_point_select);
        mBmpNormal = BitmapFactory.decodeResource(getResources(), R.drawable.guide_page_indicator_point_nomal);
    }

    public void initIndicator(int count) {
        mImageViews = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            RelativeLayout rl = new RelativeLayout(mContext);
            LayoutParams params = new LayoutParams(mHeightSelect, mHeightSelect);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            ImageView imageView = new ImageView(mContext);
            if (i == 0) {
                imageView.setImageBitmap(mBmpSelect);
                rl.addView(imageView, layoutParams);
            } else {
                imageView.setImageBitmap(mBmpNormal);
                rl.addView(imageView, layoutParams);
            }
            this.addView(rl, params);
            mImageViews.add(imageView);
        }
    }

    public void play(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            return;
        }

        final ImageView imageViewStart = mImageViews.get(startPosition);
        final ImageView imageViewNext = mImageViews.get(nextPosition);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imageViewStart, "scaleX", 1.0f, 0.25f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageViewStart, "scaleY", 1.0f, 0.25f);

        if (mOutAnimatorSet != null && mOutAnimatorSet.isRunning()) {
            mOutAnimatorSet.cancel();
            mOutAnimatorSet = null;
        }
        mOutAnimatorSet = new AnimatorSet();
        mOutAnimatorSet.play(anim1).with(anim2);
        mOutAnimatorSet.setDuration(100);

        ObjectAnimator animIn1 = ObjectAnimator.ofFloat(imageViewNext, "scaleX", 0.25f, 1.0f);
        ObjectAnimator animIn2 = ObjectAnimator.ofFloat(imageViewNext, "scaleY", 0.25f, 1.0f);

        if (mInAnimatorSet != null && mInAnimatorSet.isRunning()) {
            mInAnimatorSet.cancel();
            mInAnimatorSet = null;
        }
        mInAnimatorSet = new AnimatorSet();
        mInAnimatorSet.play(animIn1).with(animIn2);
        mInAnimatorSet.setDuration(100);

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewStart.setImageBitmap(mBmpNormal);
                ObjectAnimator animFill1 = ObjectAnimator.ofFloat(imageViewStart, "scaleX", 1.0f);
                ObjectAnimator animFill2 = ObjectAnimator.ofFloat(imageViewStart, "scaleY", 1.0f);
                AnimatorSet mFillAnimatorSet = new AnimatorSet();
                mFillAnimatorSet.play(animFill1).with(animFill2);
                mFillAnimatorSet.start();
                imageViewNext.setImageBitmap(mBmpSelect);
                mInAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mOutAnimatorSet.start();
    }
}
