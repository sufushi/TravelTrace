package com.rdc.project.traveltrace.view.puzzle_view.extend;

import android.content.Context;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleView;

public class RectanglePuzzleView extends PuzzleView {

    private static final float RADIO = 9 * 1.0f / 16;

    public RectanglePuzzleView(Context context) {
        this(context, null);
    }

    public RectanglePuzzleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectanglePuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = (int) (width * RADIO);

        setMeasuredDimension(width, height);
    }

}
