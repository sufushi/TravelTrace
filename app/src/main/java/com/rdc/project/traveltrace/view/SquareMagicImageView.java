package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.util.AttributeSet;

import com.seu.magicfilter.widget.MagicImageView;

public class SquareMagicImageView extends MagicImageView {

    public SquareMagicImageView(Context context) {
        this(context, null);
    }

    public SquareMagicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
