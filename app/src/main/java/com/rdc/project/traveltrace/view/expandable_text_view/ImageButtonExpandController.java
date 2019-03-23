package com.rdc.project.traveltrace.view.expandable_text_view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

class ImageButtonExpandController implements ExpandIndicatorController {

    private final Drawable mExpandDrawable;
    private final Drawable mCollapseDrawable;

    private ImageButton mImageButton;

    public ImageButtonExpandController(Drawable expandDrawable, Drawable collapseDrawable) {
        mExpandDrawable = expandDrawable;
        mCollapseDrawable = collapseDrawable;
    }

    @Override
    public void changeState(boolean collapsed) {
        mImageButton.setImageDrawable(collapsed ? mExpandDrawable : mCollapseDrawable);
    }

    @Override
    public void setView(View toggleView) {
        mImageButton = (ImageButton) toggleView;
    }
}

