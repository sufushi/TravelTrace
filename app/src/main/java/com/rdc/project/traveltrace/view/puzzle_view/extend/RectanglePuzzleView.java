package com.rdc.project.traveltrace.view.puzzle_view.extend;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.R;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleView;

public class RectanglePuzzleView extends PuzzleView {

    private static final float DEFAULT_RADIO = 9 * 1.0f / 16;
    private float mRadio;

    public RectanglePuzzleView(Context context) {
        this(context, null);
    }

    public RectanglePuzzleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectanglePuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectanglePuzzleView, 0, 0);
        mRadio = typedArray.getFloat(R.styleable.RectanglePuzzleView_radio, DEFAULT_RADIO);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = (int) (width * mRadio);

        setMeasuredDimension(width, height);
    }

}
