package com.rdc.project.traveltrace.view.puzzle_view.extend;

import android.content.Context;
import android.util.AttributeSet;

import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleView;

public class SquarePuzzleView extends PuzzleView {

    public SquarePuzzleView(Context context) {
        this(context, null);
    }

    public SquarePuzzleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquarePuzzleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int length = width > height ? height : width;

        setMeasuredDimension(length, length);
    }

}
