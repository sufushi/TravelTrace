package com.rdc.project.traveltrace.view.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.rdc.project.traveltrace.arch.view.IView;
import com.rdc.project.traveltrace.entity.PuzzleTemple;
import com.rdc.project.traveltrace.utils.CollectionUtil;
import com.rdc.project.traveltrace.utils.MeasureUtil;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;
import com.rdc.project.traveltrace.view.puzzle_view.extend.SquarePuzzleView;

import java.util.List;

public class PuzzleTempleItemView extends SquarePuzzleView implements IView {

    public PuzzleTempleItemView(Context context) {
        this(context, null);
    }

    public PuzzleTempleItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PuzzleTempleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int size = MeasureUtil.getScreenHeight(context) / 5;
        setLayoutParams(new ViewGroup.LayoutParams(size, size));
    }

    @Override
    public void setData(Object data) {
        if (data instanceof PuzzleTemple) {
            PuzzleTemple temple = (PuzzleTemple) data;
            PuzzleLayout layout = temple.getLayoutData();
            setNeedDrawLine(true);
            setNeedDrawOuterLine(true);
            setTouchEnable(false);
            setPuzzleLayout(layout);
            List<Bitmap> bitmaps = temple.getBitmapData();
            if (CollectionUtil.isEmpty(bitmaps)) {
                return;
            }
            int size = bitmaps.size();
            if (layout.getAreaCount() > size) {
                for (int i = 0; i < layout.getAreaCount(); i++) {
                    addPiece(bitmaps.get(i % size));
                }
            } else {
                addPieces(bitmaps);
            }
        }
    }

    @Override
    public void onActive() {

    }
}
