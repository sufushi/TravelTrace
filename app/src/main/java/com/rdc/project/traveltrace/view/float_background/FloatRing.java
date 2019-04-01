package com.rdc.project.traveltrace.view.float_background;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FloatRing extends FloatObject {

    private int mStrokeWidth;
    private int mRadius;

    public FloatRing(float posX, float posY, int strokeWidth, int radius) {
        super(posX, posY);
        mStrokeWidth = strokeWidth;
        mRadius = radius;
        setAlpha(88);
        setColor(Color.WHITE);
    }

    @Override
    public void drawFloatObject(Canvas canvas, float x, float y, Paint paint) {
        paint.setStrokeWidth(mStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, mRadius, paint);
    }

}
