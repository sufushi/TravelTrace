package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int value = getMeasuredWidth();
        setMeasuredDimension(value, value);
    }
}
