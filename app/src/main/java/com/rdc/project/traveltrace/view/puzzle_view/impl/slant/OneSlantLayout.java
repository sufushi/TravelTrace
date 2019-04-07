package com.rdc.project.traveltrace.view.puzzle_view.impl.slant;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;

public class OneSlantLayout extends NumberSlantLayout {

    public OneSlantLayout(int theme) {
        super(theme);
    }

    @Override
    public int getThemeCount() {
        return 3;
    }

    @Override
    public void layout() {
        switch (theme) {
            case 0:
                addLine(0, Line.Direction.HORIZONTAL, 0.56f, 0.44f);
                break;
            case 1:
                addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f);
                break;
            case 2:
                addCross(0, 0.56f, 0.44f, 0.56f, 0.44f);
                break;
        }
    }

}
