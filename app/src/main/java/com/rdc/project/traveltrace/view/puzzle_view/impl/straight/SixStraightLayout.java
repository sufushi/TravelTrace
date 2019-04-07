package com.rdc.project.traveltrace.view.puzzle_view.impl.straight;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;

public class SixStraightLayout extends NumberStraightLayout {

    public SixStraightLayout(int theme) {
        super(theme);
    }

    @Override
    public int getThemeCount() {
        return 12;
    }

    @Override
    public void layout() {
        switch (theme) {
            case 0:
                cutBorderEqualPart(0, 2, 1);
                break;

            case 1:
                cutBorderEqualPart(0, 1, 2);
                break;
            case 2:
                addCross(0, 2f / 3, 1f / 2);
                addLine(3, Line.Direction.VERTICAL, 1f / 2);
                addLine(2, Line.Direction.VERTICAL, 1f / 2);
                break;
            case 3:
                addCross(0, 1f / 2, 2f / 3);
                addLine(3, Line.Direction.HORIZONTAL, 1f / 2);
                addLine(1, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 4:
                addCross(0, 1f / 2, 1f / 3);
                addLine(2, Line.Direction.HORIZONTAL, 1f / 2);
                addLine(0, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 5:
                addCross(0, 1f / 3, 1f / 2);
                addLine(1, Line.Direction.VERTICAL, 1f / 2);
                addLine(0, Line.Direction.VERTICAL, 1f / 2);
                break;
            case 6:
                addLine(0, Line.Direction.HORIZONTAL, 4f / 5);
                cutBorderEqualPart(1, 5, Line.Direction.VERTICAL);
                break;
            case 7:
                addLine(0, Line.Direction.HORIZONTAL, 1f / 4);
                addLine(1, Line.Direction.HORIZONTAL, 2f / 3);
                addLine(1, Line.Direction.VERTICAL, 1f / 4);
                addLine(2, Line.Direction.VERTICAL, 2f / 3);
                addLine(4, Line.Direction.VERTICAL, 1f / 2);
                break;
            case 8:
                addCross(0, 1f / 3);
                addLine(1, Line.Direction.VERTICAL, 1f / 2);
                addLine(4, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 9:
                addCross(0, 2f / 3, 1f / 3);
                addLine(3, Line.Direction.VERTICAL, 1f / 2);
                addLine(0, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 10:
                addCross(0, 2f / 3);
                addLine(2, Line.Direction.VERTICAL, 1f / 2);
                addLine(1, Line.Direction.HORIZONTAL, 1f / 2);
                break;
            case 11:
                addCross(0, 1f / 3, 2f / 3);
                addLine(3, Line.Direction.HORIZONTAL, 1f / 2);
                addLine(0, Line.Direction.VERTICAL, 1f / 2);
                break;
            case 12:
                addCross(0, 1f / 3);
                addLine(2, Line.Direction.HORIZONTAL, 1f / 2);
                addLine(1, Line.Direction.VERTICAL, 1f / 2);
                break;
            default:
                addCross(0, 2f / 3, 1f / 2);
                addLine(3, Line.Direction.VERTICAL, 1f / 2);
                addLine(2, Line.Direction.VERTICAL, 1f / 2);
                break;
        }
    }

}