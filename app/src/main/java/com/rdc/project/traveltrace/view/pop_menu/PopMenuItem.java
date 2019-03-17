package com.rdc.project.traveltrace.view.pop_menu;

import android.graphics.drawable.Drawable;

public class PopMenuItem {

    private String mTitle;
    private Drawable mDrawable;

    public PopMenuItem(String title, Drawable drawable) {
        mTitle = title;
        mDrawable = drawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
