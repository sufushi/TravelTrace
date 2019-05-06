package com.rdc.project.traveltrace.view.h5_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import java.util.Calendar;

public class ClickableWebView extends WebView implements View.OnClickListener, View.OnTouchListener {

    private static final int MAX_CLICK_DURATION = 200;
    private static final int IMAGE_TYPE = 5;

    private WebViewClickCallback mCallback;
    private long mStartClickTime;

    public ClickableWebView(Context context) {
        this(context, null);
    }

    public ClickableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClickableWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public ClickableWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setOnTouchListener(this);
    }

    public void setOnWebViewClickListener(WebViewClickCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onClick(View view) {
        WebView.HitTestResult hr = getHitTestResult();
        try {
            if (mCallback != null && hr.getType() == IMAGE_TYPE) {
                mCallback.onClick(hr.getExtra());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mStartClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - mStartClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    performClick();
                }
            }
        }
        return false;
    }
}
