package com.rdc.project.traveltrace.entity;

import java.util.List;

public class PuzzleTempleList {

    private List<PuzzleTemple> mPuzzleTempleList;

    public PuzzleTempleList(List<PuzzleTemple> puzzleTempleList) {
        mPuzzleTempleList = puzzleTempleList;
    }

    public List<PuzzleTemple> getPuzzleTempleList() {
        return mPuzzleTempleList;
    }

    public void setPuzzleTempleList(List<PuzzleTemple> puzzleTempleList) {
        mPuzzleTempleList = puzzleTempleList;
    }

    @Override
    public String toString() {
        return "PuzzleTempleList{" +
                "mPuzzleTempleList=" + mPuzzleTempleList +
                '}';
    }
}
