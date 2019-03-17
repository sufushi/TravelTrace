package com.rdc.project.traveltrace.view.oblique_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ObliqueView extends android.support.v7.widget.AppCompatImageView {

    private Path mShadowPath, mPath;
    private float mWidth, mHeight;
    private Config mConfig = null;
    private Bitmap mBitmap = null;
    private Paint mPaint = new Paint(ANTI_ALIAS_FLAG);
    private PorterDuffXfermode mPdMode;

    public ObliqueView(Context context) {
        this(context, null);
    }

    public ObliqueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObliqueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mConfig = new Config(context, attrs);
        mPdMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    }

    public GradientAngle getAngle() {
        return mConfig.getAngle();
    }

    public void setAngle(GradientAngle angle) {
        mConfig.setAngle(angle);
        invalidate();
    }

    public void setShadow(float elevation) {
        mConfig.setElevation(elevation);
        invalidate();
    }

    public int getStartColor() {
        return mConfig.getStartColor();
    }

    public void setStartColor(int startColor) {
        mConfig.setStartColor(startColor);
        invalidate();
    }


    public int getEndColor() {
        return mConfig.getEndColor();
    }

    public void setEndColor(int endColor) {
        mConfig.setEndColor(endColor);
        invalidate();
    }


    public float getStartAngle() {
        return mConfig.getStartAngle();
    }

    public void setStartAngle(@FloatRange(from = 0, to = 180) float startAngle) {
        mConfig.setStartAngle(startAngle);
        invalidate();
    }


    public float getEndAngle() {
        return mConfig.getEndAngle();
    }

    public void setEndAngle(@FloatRange(from = 0, to = 180) float endAngle) {
        mConfig.setEndAngle(endAngle);
        invalidate();
    }


    public float getCornerRadius() {
        return mConfig.getRadius();
    }

    public void setCornerRadius(@FloatRange(from = 0, to = 60) float radius) {
        mConfig.setRadius(radius <= 60 ? radius : 60);
        invalidate();
    }

    public int getBaseColor() {
        return mConfig.getBaseColor();
    }

    public void setBaseColor(int baseColor) {
        mConfig.setBaseColor(baseColor);
        invalidate();
    }

    public int getType() {
        return mConfig.getBaseColor();
    }

    public void setType(Type type) {
        mConfig.setType(type);
        invalidate();
    }

    private void setupBitmap(ImageView imageView, float width, float height) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            return;
        }
        try {
            if (drawable instanceof BitmapDrawable) {
                mBitmap = ((BitmapDrawable) drawable).getBitmap();
            } else {
                int w = drawable.getIntrinsicWidth();
                if (w <= 0) {
                    w = this.getWidth();
                }
                int h = drawable.getIntrinsicHeight();
                if (h <= 0) {
                    h = this.getHeight();
                }
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                mBitmap = Bitmap.createBitmap(w, h, config);
                Canvas canvas = new Canvas(mBitmap);
                drawable.setBounds(0, 0, w, h);
                drawable.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mBitmap == null) {
            imageView.invalidate();
            return;
        }
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP && imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        bitmapShader.setLocalMatrix(setUpScaleType(mBitmap, imageView, width, height));
        imageView.invalidate();
    }

    private Matrix setUpScaleType(Bitmap bitmap, ImageView iv, float width, float height) {
        float scaleX = 1, scaleY = 1, dx = 0, dy = 0;
        Matrix shaderMatrix = new Matrix();
        if (bitmap == null) {
            return null;
        }
        shaderMatrix.set(null);
        if (iv.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
            if (width != bitmap.getWidth()) {
                scaleX = width / bitmap.getWidth();
            }
            if (scaleX * bitmap.getHeight() < height) {
                scaleX = height / bitmap.getHeight();
            }
            dy = (height - bitmap.getHeight() * scaleX) * 0.5f;
            dx = (width - bitmap.getWidth() * scaleX) * 0.5f;
            shaderMatrix.setScale(scaleX, scaleX);
        } else {
            scaleX = width / bitmap.getWidth();
            scaleY = height / bitmap.getHeight();
            dy = (height - bitmap.getHeight() * scaleY) * 0.5f;
            dx = (width - bitmap.getWidth() * scaleX) * 0.5f;
            shaderMatrix.setScale(scaleX, scaleY);
        }
        shaderMatrix.postTranslate(dx + 0.5f, dy + 0.5f);
        return shaderMatrix;
    }

    //Overriden Methods
    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setupBitmap(this, mWidth, mHeight);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setupBitmap(this, mWidth, mHeight);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setupBitmap(this, mWidth, mHeight);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setupBitmap(this, mWidth, mHeight);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.CENTER_CROP || scaleType == ScaleType.FIT_XY)
            super.setScaleType(scaleType);
        else
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mPath = mConfig.getPath(mHeight, mWidth);
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewOutlineProvider getOutlineProvider() {
        mShadowPath = new Path();
        if (mConfig.getRadius() == 0) {
            mShadowPath = mPath;
        } else {
            Rect rect = new Rect(0, 0, (int) mWidth, (int) mHeight);
            RectF r = new RectF(rect);
            mShadowPath.addRoundRect(r, mConfig.getRadius(), mConfig.getRadius(), Path.Direction.CCW);
            mShadowPath.op(mPath, mShadowPath, Path.Op.INTERSECT);
        }
        return new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (mPath.isConvex()) {
                    outline.setConvexPath(mShadowPath);
                }
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        switch (mConfig.getType()) {
            case 0:
                mPaint.setColor(mConfig.getBaseColor());
                break;
            case 1:
                mPaint.setShader(mConfig.getLinearGradient(mConfig.getAngle(), mWidth, mHeight));
                break;
            case 2:
                mPaint.setShader(mConfig.getRadialGradient(mWidth, mHeight));
                break;
            case 3:
                setupBitmap(this, mWidth, mHeight);
                break;
        }
        mPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        mPaint.setStrokeCap(Paint.Cap.ROUND);      // set the mPaint cap to round too
        mPaint.setPathEffect(new CornerPathEffect(mConfig.getRadius()));
        ViewCompat.setElevation(this, mConfig.getShadow());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ViewCompat.getElevation(this) > 0f) {

            try {
                setOutlineProvider(getOutlineProvider());
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                e.printStackTrace();
            }
        }
        mPaint.setXfermode(mPdMode);
        canvas.drawPath(mPath, mPaint);
    }
}
