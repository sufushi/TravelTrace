package com.rdc.project.traveltrace.view.float_background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

public class FloatBitmap extends FloatObject {

    private Bitmap mBitmap;

    public FloatBitmap(Context context, float posX, float posY, int resId) {
        super(posX, posY);
        setAlpha(120);
        setColor(Color.WHITE);
        mBitmap = ((BitmapDrawable) context.getResources().getDrawable(resId)).getBitmap();
    }

    @Override
    public void drawFloatObject(Canvas canvas, float x, float y, Paint paint) {
        canvas.drawBitmap(mBitmap, x, y, paint);
    }

}
