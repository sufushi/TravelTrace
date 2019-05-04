package com.rdc.project.traveltrace.manager;

import android.graphics.drawable.Drawable;

public class PuzzleHandlePieceManager {

    private Drawable mPieceDrawable;
    private String mPath;

    private PuzzleHandlePieceManager() {

    }

    private static class PuzzleHandlePieceHolder {
        private static final PuzzleHandlePieceManager INSTANCE = new PuzzleHandlePieceManager();
    }

    public static PuzzleHandlePieceManager getInstance() {
        return PuzzleHandlePieceHolder.INSTANCE;
    }

    public void setPieceDrawable(Drawable drawable) {
        mPieceDrawable = drawable;
    }

    public Drawable getPieceDrawable() {
        return mPieceDrawable;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void recycle() {
        mPieceDrawable = null;
        mPath = "";
    }
}
