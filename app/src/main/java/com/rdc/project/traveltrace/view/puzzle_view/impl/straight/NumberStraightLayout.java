package com.rdc.project.traveltrace.view.puzzle_view.impl.straight;

import android.util.Log;

import com.rdc.project.traveltrace.view.puzzle_view.straight.StraightPuzzleLayout;

public abstract class NumberStraightLayout extends StraightPuzzleLayout {

    private static final String TAG = "NumberStraightLayout";
    protected int theme;

    public NumberStraightLayout(int theme) {
        if (theme >= getThemeCount()) {
            Log.e(TAG, "NumberStraightLayout: the most theme count is "
                    + getThemeCount()
                    + " ,you should let theme from 0 to "
                    + (getThemeCount() - 1)
                    + " .");
        }
        this.theme = theme;
    }

    public abstract int getThemeCount();

    public int getTheme() {
        return theme;
    }

}
