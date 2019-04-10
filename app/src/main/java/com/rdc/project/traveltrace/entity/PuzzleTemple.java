package com.rdc.project.traveltrace.entity;

import android.graphics.Bitmap;

import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;

import java.util.List;

public class PuzzleTemple {

    private PuzzleLayout mLayoutData;
    private List<Bitmap> mBitmapData;

    public PuzzleTemple(PuzzleLayout layoutData, List<Bitmap> bitmapData) {
        mLayoutData = layoutData;
        mBitmapData = bitmapData;
    }

    public PuzzleLayout getLayoutData() {
        return mLayoutData;
    }

    public void setLayoutData(PuzzleLayout layoutData) {
        mLayoutData = layoutData;
    }

    public List<Bitmap> getBitmapData() {
        return mBitmapData;
    }

    public void setBitmapData(List<Bitmap> bitmapData) {
        mBitmapData = bitmapData;
    }

    @Override
    public String toString() {
        return "PuzzleTemple{" +
                "mLayoutData=" + mLayoutData +
                ", mBitmapData=" + mBitmapData +
                '}';
    }
}
