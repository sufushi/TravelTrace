package com.rdc.project.traveltrace.view.float_background;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

public abstract class FloatObject {

    public static final int START = 0;
    public static final int MOVE = 1;
    public static final int END = 2;
    public static final int FINISH = 3;

    private int mStatus;
    // percent
    float mPosX;
    float mPosY;

    private int mWidth;
    private int mHeight;
    private float mX;
    private float mY;
    private int mAlpha;

    private float mDistance = 500;
    private float mCurDistance = 0;

    private PointF mStart;
    private Point mEnd;
    private Point mC1;
    private Point mC2;

    private Random mRandom = new Random();
    private Paint mPaint = new Paint();

    private int ALPHA_LIMIT = 255;

    private static final float MOVE_PER_FRAME = 0.4F;
    private static final int ALPHA_PER_FRAME = 2;

    public FloatObject(float posX, float posY) {
        this.mPosX = posX;
        this.mPosY = posY;
    }

    public abstract void drawFloatObject(Canvas canvas, float x, float y, Paint paint);

    public void init(int x, int y, int width, int height) {
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;

        mPaint.setAntiAlias(true);
        setStatus(FINISH);
    }

    public void drawFloatItem(Canvas canvas) {

        switch (mStatus) {
            case START:
                // fade in
                if (isFade() && mAlpha <= ALPHA_LIMIT) {
                    mPaint.setAlpha(mAlpha);
                    mAlpha += ALPHA_PER_FRAME;
                } else {
                    setStatus(MOVE);
                }
                break;
            case MOVE:
                // 更新赛贝尔曲线点
                if (mCurDistance == 0) {
                    mStart = new PointF(mX, mY);
                    mEnd = getRandomPoint((int) mStart.x, (int) mStart.y, (int) mDistance);// 取值范围distance
                    mC1 = getRandomPoint((int) mStart.x, (int) mStart.y, mRandom.nextInt(mWidth / 2)); // 取值范围width/2
                    mC2 = getRandomPoint(mEnd.x, mEnd.y, mRandom.nextInt(mWidth / 2));// 取值范围width/2
                }

                // 计算塞贝儿曲线的当前点
                PointF bezierPoint = calculateBezierPoint(mCurDistance / mDistance, mStart, mC1, mC2, mEnd);
                mX = bezierPoint.x;
                mY = bezierPoint.y;

                // 更新当前路径
                mCurDistance += MOVE_PER_FRAME;

                // 一段画完后重置
                if (mCurDistance >= mDistance) {
                    mCurDistance = 0;
                }
                break;
            case END:
                // fade out
                if (isFade() && mAlpha > 0) {
                    mPaint.setAlpha(mAlpha);
                    mAlpha -= ALPHA_PER_FRAME;
                } else {
                    setStatus(FINISH);
                }
                break;
        }

        if (mStatus != FINISH) {
            Log.e("drawFloatObject", mX +", "+ mY);
            drawFloatObject(canvas, mX, mY, mPaint);
        }
    }

    /**
     * 计算塞贝儿曲线
     *
     * @param t  时间，范围0-1
     * @param s  起始点
     * @param c1 拐点1
     * @param c2 拐点2
     * @param e  终点
     * @return 塞贝儿曲线在当前时间下的点
     */
    private PointF calculateBezierPoint(float t, PointF s, Point c1, Point c2, Point e) {
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        PointF p = new PointF((s.x * uuu), (s.y * uuu));
        p.x += 3 * uu * t * c1.x;
        p.y += 3 * uu * t * c1.y;
        p.x += 3 * u * tt * c2.x;
        p.y += 3 * u * tt * c2.y;
        p.x += ttt * e.x;
        p.y += ttt * e.y;

        return p;
    }


    /**
     * 根据基准点获取指定范围为半径的随机点
     */
    private Point getRandomPoint(int baseX, int baseY, int r) {
        if (r <= 0) {
            r = 1;
        }
        int x = mRandom.nextInt(r);
        int y = (int) Math.sqrt(r * r - x * x);

        x = baseX + getRandomPNValue(x);
        y = baseY + getRandomPNValue(y);

//        if (mX > mWidth || mX < 0 || mY > mHeight || mY < 0) {
//            return getRandomPoint(baseX, baseY, r);
//        }

        if (x > mWidth) {
            x = mWidth - r;
        } else if (x < 0) {
            x = r;
        } else if (y > mHeight) {
            y = mHeight - r;
        } else if (y < 0) {
            y = r;
        }

        return new Point(x, y);
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    /**
     * 获取随机正负数
     */
    private int getRandomPNValue(int value) {
        return mRandom.nextBoolean() ? value : 0 - value;
    }

    public void setAlpha(int alpha) {
        ALPHA_LIMIT = alpha;
    }

    public boolean isFade() {
        return true;
    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

}
