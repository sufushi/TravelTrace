package com.rdc.project.traveltrace.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.rdc.project.traveltrace.app.App;

import java.util.Objects;

public class TintUtil {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void tint(ImageView imageView, int redId, int color) {
        if (imageView == null || redId == 0) {
            return;
        }
        final Drawable origin = Objects.requireNonNull(App.getAppContext().getDrawable(redId)).mutate();
        imageView.setImageDrawable(tintDrawable(origin, ColorStateList.valueOf(App.getAppContext().getResources().getColor(color))));
    }

    private static Drawable tintDrawable(Drawable drawable, ColorStateList color) {
        final Drawable tempDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(tempDrawable, color);
        return tempDrawable;
    }

}
