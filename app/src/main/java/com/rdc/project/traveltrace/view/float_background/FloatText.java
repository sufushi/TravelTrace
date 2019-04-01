package com.rdc.project.traveltrace.view.float_background;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FloatText extends FloatObject {

    private String mText;

    public FloatText(float posX, float posY, String text) {
        super(posX, posY);
        mText = text;
        setAlpha(88);
        setColor(Color.WHITE);
    }

    @Override
    public void drawFloatObject(Canvas canvas, float x, float y, Paint paint) {
        paint.setTextSize(65);
        canvas.drawText(mText, x, y, paint);
    }

}
