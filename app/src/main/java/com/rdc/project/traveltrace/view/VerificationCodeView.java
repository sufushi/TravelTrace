package com.rdc.project.traveltrace.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Random;

public class VerificationCodeView extends View {
    //将图片划分成4*3个小格
    private static final int WIDTH = 4;
    private static final int HEIGHT = 3;
    //小格相交的总的点数
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] array1 = new float[COUNT * 2];
    private float[] array2 = new float[COUNT * 2];

    //黄背景颜色
    private int YELLOW_BG_COLOR = 0xfff9dec1;

    private Rect mTextBound;//用于计算文本的宽高

    private Paint mTextPaint;
    private Paint mLinePaint;

    private float mDefaultTextSize = 0;
    private String mTempCode = "";//当前生成的验证码
    private int mCodeNum = 4;//验证码位数  4或6。。
    private Random mRandom;

    //控件总宽度
    private int mWidth;
    //控件高度
    private int mHeight;
    private int horizontalOffset = 0;//水平方向的偏移，决定画笔开始的偏移量
    private boolean isInit = false;//是否初始化完成
    private boolean isWrapContent = true;//是否是WrapContent属性

    private Bitmap mBitmap;
    private Bitmap mCodeBitmap;
    /**
     * 绘制贝塞尔曲线的路径集合
     */
    private ArrayList<Path> mPaths = new ArrayList<Path>();
    private String vCode;
    private boolean isNetCode = false;//是否是网络请求到验证码

    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽和高的SpecMode和SpecSize
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            mWidth = widthSize;
            isWrapContent = false;
        } else {//没有设置宽度，先默认显示四个0的宽度
            isWrapContent = true;
            mWidth = (int) (mTextPaint.measureText("0") * 1.5 * mCodeNum + 20 + DensityUtil.dp2px(8, getContext()));
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            mHeight = heightSize;
        } else {//没有设置高度，先默认显示40dp
            mHeight = DensityUtil.dp2px(40, getContext());
        }

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        isInit = true;
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCodeBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        if (!TextUtils.isEmpty(mTempCode)) {
            createCodeBitmap();
        }
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        isNetCode = typedArray.getBoolean(R.styleable.VerificationCodeView_isNetCode, false);
        mCodeNum = typedArray.getInteger(R.styleable.VerificationCodeView_codeNumber, 4);
        typedArray.recycle();
    }

    private void initView() {
        mRandom = new Random();

        //背景画笔
        Paint bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(YELLOW_BG_COLOR);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mDefaultTextSize = DensityUtil.sp2px(getContext(), 30);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mDefaultTextSize);
        mTextPaint.setShadowLayer(5, 3, 3, 0xFF999999);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextScaleX(0.8F);
        mTextPaint.setColor(Color.GREEN);

        mTextBound = new Rect();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCodeBitmap, 0, 0, null);
    }

    private void createCodeBitmap() {
        if (!isInit)
            return;
        mPaths.clear();
        if (!isWrapContent) {
            dynamicSetTextPaint(mTempCode);
            float drawWidth = mTextPaint.measureText(mTempCode) * 3 / 2;
            horizontalOffset = (int) ((mWidth - drawWidth - DensityUtil.dp2px(8, getContext())) / 2);
            if (horizontalOffset < 0) {
                horizontalOffset = 0;
            }
        }
        // 生成干扰线坐标
        for (int i = 0; i < 2; i++) {
            Path path = new Path();
            int startX = mRandom.nextInt(mWidth / 3) + 10;
            int startY = mRandom.nextInt(mHeight / 3) + 10;
            int endX = mRandom.nextInt(mWidth / 2) + mWidth / 2 - 10;
            int endY = mRandom.nextInt(mHeight / 2) + mHeight / 2 - 10;
            path.moveTo(startX, startY);
            path.quadTo(Math.abs(endX - startX) / 2.0f, Math.abs(endY - startY) / 2.0f, endX, endY);
            mPaths.add(path);
        }
        Canvas myCanvas = new Canvas(mBitmap);
        Canvas canvas = new Canvas(mCodeBitmap);
        //画背景
        myCanvas.drawColor(YELLOW_BG_COLOR);
        if (mTempCode != null && mTempCode.length() > 0) {
            mTextPaint.getTextBounds(mTempCode, 0, mCodeNum, mTextBound);
            float charLength = (mTextBound.width()) * 1.0f / mCodeNum;
            for (int i = 0; i < mCodeNum; i++) {
                int offsetDegree = mRandom.nextInt(15);
                // 这里只会产生0和1，如果是1那么正旋转正角度，否则旋转负角度
                offsetDegree = mRandom.nextInt(2) == 1 ? offsetDegree : -offsetDegree;
                myCanvas.save();
                myCanvas.rotate(offsetDegree, mWidth / 2, mHeight / 2);
                // 给画笔设置随机颜色，+20是为了去除一些边界值
                mTextPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
                myCanvas.drawText(String.valueOf(mTempCode.charAt(i)), i * charLength * 1.5f + 20 + horizontalOffset, mHeight * 2 / 3f, mTextPaint);
                myCanvas.restore();
            }
        }
        int index = 0;
        float bitmap_width = mBitmap.getWidth();
        float bitmap_height = mBitmap.getHeight();
        for (int i = 0; i < HEIGHT + 1; i++) {
            float fy = bitmap_height / HEIGHT * i;
            for (int j = 0; j < WIDTH + 1; j++) {
                float fx = bitmap_width / WIDTH * j;
                //偶数位记录x坐标  奇数位记录Y坐标
                array2[index * 2] = array1[index * 2] = fx;
                array2[index * 2 + 1] = array1[index * 2 + 1] = fy;
                index++;
            }
        }
        //设置变形点，这些点将会影响变形的效果
        //扭曲偏移
        float offset = bitmap_height / HEIGHT / 3;
        array1[12] = array1[12] - offset;
        array1[13] = array1[13] + offset;
        array1[16] = array1[16] + offset;
        array1[17] = array1[17] - offset;
        array1[24] = array1[24] + offset;
        array1[25] = array1[25] + offset;

        // 对验证码图片进行扭曲变形
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, array1, 0, null, 0, null);
        // 产生干扰效果2 -- 干扰线
        for (Path path : mPaths) {
            mLinePaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            canvas.drawPath(path, mLinePaint);
        }
    }

    private void dynamicSetTextPaint(String text) {
        int width = (int) (mWidth * 2.0f / 3 - getPaddingLeft() - getPaddingRight() - DensityUtil.dp2px(2, getContext()));
        if (width <= 0) {
            return;
        }
        float trySize = mDefaultTextSize;
        while (mTextPaint.measureText(text) > width) {
            trySize--;
            mTextPaint.setTextSize(trySize);
        }
    }

    public String getCharAndNumber() {
        if (isNetCode) {
            mCodeNum = vCode != null ? vCode.length() : 0;
            return vCode;
        } else {
            StringBuilder val = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < mCodeNum; i++) {
                // 输出字母还是数字
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                // 字符串
                if ("char".equalsIgnoreCase(charOrNum)) {
                    // 取得大写字母还是小写字母
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val.append((char) (choice + random.nextInt(26)));
                } else { // 数字
                    val.append(String.valueOf(random.nextInt(10)));
                }
            }
            vCode = val.toString();
            return val.toString();
        }
    }

    public void refreshCode() {
        mTempCode = getCharAndNumber();
        if (mTempCode == null || mTempCode.length() <= 0) {
            return;
        }
        if (isInit) {
            createCodeBitmap();
            requestLayout();
            invalidate();
        }
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String code) {
        this.vCode = code;
        refreshCode();
    }
}