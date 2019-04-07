package com.rdc.project.traveltrace.view.puzzle_view.impl.straight;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;

public class OneStraightLayout extends NumberStraightLayout {

    public OneStraightLayout(int theme) {
        super(theme);
    }

    @Override
    public int getThemeCount() {
        return 6;
    }

    @Override
    public void layout() {
        switch (theme) {
            case 0:
                addLine(0, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 1:
                addLine(0, Line.Direction.VERTICAL, 1f / 2);
                break;
            case 2:
                addCross(0, 1f / 2);
                break;
            case 3:
                cutBorderEqualPart(0, 2, 1);
                break;
            case 4:
                cutBorderEqualPart(0, 1, 2);
                break;
            case 5:
                cutBorderEqualPart(0, 2, 2);
                break;
            default:
                addLine(0, Line.Direction.HORIZONTAL, 1f / 2);
                break;
        }
    }

}
