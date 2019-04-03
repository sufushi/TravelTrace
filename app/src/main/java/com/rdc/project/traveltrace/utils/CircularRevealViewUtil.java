package com.rdc.project.traveltrace.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

public class CircularRevealViewUtil {

    public static <V extends View> void doAnim(V view, int delay) {
        view.setVisibility(View.INVISIBLE);
        view.postDelayed(() -> {
            if (view.isAttachedToWindow()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    int x = view.getMeasuredWidth() / 2;
                    int y = view.getMeasuredWidth() / 3;
                    int radius = Math.max(x, y);
                    Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, radius);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
                    animator.setDuration(400);
                    animator.start();
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }, delay);
    }
}
