package com.rdc.project.traveltrace.view.puzzle_view.impl.controller;

import android.content.Context;

import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleView;
import com.rdc.project.traveltrace.view.puzzle_view.impl.ui.PuzzlePanelView;
import com.rdc.project.traveltrace.view.puzzle_view.util.FileUtils;

import java.io.File;

public class PuzzlePanelController implements IPuzzleController {

    private PuzzlePanelView mPuzzlePanelView;
    private Context mContext;
    private PuzzleView mPuzzleView;

    public PuzzlePanelController(Context context, PuzzleView puzzleView) {
        mContext = context;
        mPuzzleView = puzzleView;
    }

    @Override
    public void replace() {

    }

    @Override
    public void rotate() {
        mPuzzleView.rotate(90);
    }

    @Override
    public void flipHorizontal() {
        mPuzzleView.flipHorizontally();
    }

    @Override
    public void flipVertical() {
        mPuzzleView.flipVertically();
    }

    @Override
    public void border() {
        boolean isNeedDrawLine = mPuzzleView.isNeedDrawLine();
        mPuzzleView.setNeedDrawLine(!isNeedDrawLine);
    }

    @Override
    public void filter() {

    }

    @Override
    public void save() {
        File file = FileUtils.getNewFile(mContext, "puzzle");
        FileUtils.savePuzzle(mPuzzleView, file, 100, new FileUtils.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public void share() {

    }

    @Override
    public void temple() {

    }
}
