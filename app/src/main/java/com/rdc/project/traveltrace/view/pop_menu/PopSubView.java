package com.rdc.project.traveltrace.view.pop_menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopSubView extends LinearLayout {

    private static final float FACTOR = 1.2f;

    private ImageView mIconView;
    private TextView mTextView;

    public ImageView getIconView() {
        return mIconView;
    }

    public void setIconView(ImageView iconView) {
        this.mIconView = iconView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        this.mTextView = textView;
    }

    public PopSubView(Context context) {
        this(context, null);
    }

    public PopSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        mIconView = new ImageView(context);
        mIconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(mIconView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mTextView = new TextView(context);
        LayoutParams tvLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.topMargin = 10;
        addView(mTextView, tvLp);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scaleViewAnimation(PopSubView.this, FACTOR);
                        break;
                    case MotionEvent.ACTION_UP:
                        scaleViewAnimation(PopSubView.this, 1);
                        break;
                }
                return false;
            }
        });
    }

    public void setPopMenuItem(PopMenuItem popMenuItem) {
        if (popMenuItem == null) return;
        mIconView.setImageDrawable(popMenuItem.getDrawable());
        mTextView.setText(popMenuItem.getTitle());
    }

    private void scaleViewAnimation(View view, float value) {
        view.animate().scaleX(value).scaleY(value).setDuration(80).start();
    }

}
