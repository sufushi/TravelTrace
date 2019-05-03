package com.rdc.project.traveltrace.view.float_background;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.rdc.project.traveltrace.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class FloatBackground extends FrameLayout {

    private static final long DELAY = 26;

    private List<FloatObject> mFloats = new ArrayList<>();

    public FloatBackground(Context context) {
        this(context, null);
    }

    public FloatBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            initFloatObject(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (FloatObject floatObject : mFloats) {
            floatObject.drawFloatItem(canvas);
        }
        // 隔一段时间重绘一次, 动画效果
        getHandler().postDelayed(mRunnable, DELAY);

    }

    // 控制帧数
    // 重绘线程
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public void startFloat() {
        for (FloatObject floatObject : mFloats) {
            floatObject.setStatus(FloatObject.START);
        }
    }

    public void endFloat() {
        for (FloatObject floatObject : mFloats) {
            floatObject.setStatus(FloatObject.END);
        }
    }

    public void addFloatView(FloatObject floatObject) {
        if (floatObject == null) {
            return;
        }
        mFloats.add(floatObject);
    }

    public void addFloatViewList(List<FloatObject> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        mFloats.addAll(list);
    }

    private void initFloatObject(int width, int height) {
        for (FloatObject floatObject : mFloats) {
            int x = (int) (floatObject.mPosX * width);
            int y = (int) (floatObject.mPosY * height);
            floatObject.init(x, y, width, height);
        }
    }

}
