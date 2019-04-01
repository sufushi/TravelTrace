package com.rdc.project.traveltrace.view.float_background;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class FloatRect extends FloatObject {

    private int mRotate;
    private int mWidth;

    public FloatRect(float posX, float posY, int rotate, int width) {
        super(posX, posY);
        mRotate = rotate;
        mWidth = width;
        setAlpha(88);
        setColor(Color.WHITE);
    }

    @Override
    public void drawFloatObject(Canvas canvas, float x, float y, Paint paint) {
        RectF f = new RectF(x - (mWidth >> 3), y - mWidth, x + (mWidth >> 3), y + mWidth);
        canvas.drawRect(f, paint);
    }

}
