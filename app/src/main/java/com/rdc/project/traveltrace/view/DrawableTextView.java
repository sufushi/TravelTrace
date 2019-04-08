package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rdc.project.traveltrace.R;

public class DrawableTextView extends TextView {

    private int mDrawableWidth;
    private int mDrawableHeight;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        int count = typedArray.getIndexCount();
        Drawable drawableLeft = null;
        Drawable drawableTop = null;
        Drawable drawableRight = null;
        Drawable drawableBottom = null;
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.DrawableTextView_drawableRight:
                    drawableRight = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableLeft:
                    drawableLeft = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableTop:
                    drawableTop = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableBottom:
                    drawableBottom = typedArray.getDrawable(attr);
                    break;
                case R.styleable.DrawableTextView_drawableWidth:
                    mDrawableWidth = typedArray.getDimensionPixelSize(attr, 0);
                    break;
                case R.styleable.DrawableTextView_drawableHeight:
                    mDrawableHeight = typedArray.getDimensionPixelSize(attr, 0);
                    break;
            }
        }
        if (null != drawableLeft) {
            drawableLeft.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (null != drawableRight) {
            drawableRight.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (null != drawableTop) {
            drawableTop.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (null != drawableBottom) {
            drawableBottom.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        typedArray.recycle();
    }
}
