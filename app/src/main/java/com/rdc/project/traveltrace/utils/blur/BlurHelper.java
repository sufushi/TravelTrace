package com.rdc.project.traveltrace.utils.blur;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AlphaAnimation;

final class BlurHelper {

    public static void setBackground(View v, Drawable drawable) {
        v.setBackground(drawable);
    }

    public static boolean hasZero(int... args) {
        for (int num : args) {
            if (num == 0) {
                return true;
            }
        }
        return false;
    }

    public static void animate(View v, int duration) {
        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(duration);
        v.startAnimation(alpha);
    }
}
