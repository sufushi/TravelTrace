package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RectangleImageView extends ImageView {

    private float mRadio = 1.5f;

    public RectangleImageView(Context context) {
        this(context, null);
    }

    public RectangleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectangleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRadio(float radio) {
        mRadio = radio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (width * mRadio);
        setMeasuredDimension(width, height);
    }
}
