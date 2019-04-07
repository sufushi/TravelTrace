package com.rdc.project.traveltrace.view.puzzle_view.impl.straight;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;

public class NineStraightLayout extends NumberStraightLayout {

    public NineStraightLayout(int theme) {
        super(theme);
    }

    @Override
    public int getThemeCount() {
        return 8;
    }

    @Override
    public void layout() {
        switch (theme) {
            case 0:
                cutBorderEqualPart(0, 2, 2);
                break;
            case 1:
                addLine(0, Line.Direction.VERTICAL, 3f / 4);
                addLine(0, Line.Direction.VERTICAL, 1f / 3);
                cutBorderEqualPart(2, 4, Line.Direction.HORIZONTAL);
                cutBorderEqualPart(0, 4, Line.Direction.HORIZONTAL);
                break;
            case 2:
                addLine(0, Line.Direction.HORIZONTAL, 3f / 4);
                addLine(0, Line.Direction.HORIZONTAL, 1f / 3);
                cutBorderEqualPart(2, 4, Line.Direction.VERTICAL);
                cutBorderEqualPart(0, 4, Line.Direction.VERTICAL);
                break;
            case 3:
                addLine(0, Line.Direction.HORIZONTAL, 3f / 4);
                addLine(0, Line.Direction.HORIZONTAL, 1f / 3);
                cutBorderEqualPart(2, 3, Line.Direction.VERTICAL);
                addLine(1, Line.Direction.VERTICAL, 3f / 4);
                addLine(1, Line.Direction.VERTICAL, 1f / 3);
                cutBorderEqualPart(0, 3, Line.Direction.VERTICAL);
                break;
            case 4:
                addLine(0, Line.Direction.VERTICAL, 3f / 4);
                addLine(0, Line.Direction.VERTICAL, 1f / 3);
                cutBorderEqualPart(2, 3, Line.Direction.HORIZONTAL);
                addLine(1, Line.Direction.HORIZONTAL, 3f / 4);
                addLine(1, Line.Direction.HORIZONTAL, 1f / 3);
                cutBorderEqualPart(0, 3, Line.Direction.HORIZONTAL);
                break;
            case 5:
                cutBorderEqualPart(0, 3, Line.Direction.VERTICAL);
                addLine(2, Line.Direction.HORIZONTAL, 3f / 4);
                addLine(2, Line.Direction.HORIZONTAL, 1f / 3);
                cutBorderEqualPart(1, 3, Line.Direction.HORIZONTAL);
                addLine(0, Line.Direction.HORIZONTAL, 3f / 4);
                addLine(0, Line.Direction.HORIZONTAL, 1f / 3);
                break;
            case 6:
                cutBorderEqualPart(0, 3, Line.Direction.HORIZONTAL);
                addLine(2, Line.Direction.VERTICAL, 3f / 4);
                addLine(2, Line.Direction.VERTICAL, 1f / 3);
                cutBorderEqualPart(1, 3, Line.Direction.VERTICAL);
                addLine(0, Line.Direction.VERTICAL, 3f / 4);
                addLine(0, Line.Direction.VERTICAL, 1f / 3);
                break;
            case 7:
                addLine(0, Line.Direction.HORIZONTAL, 1f / 2);
                cutBorderEqualPart(1, 1, 3);
                break;
            default:
                break;
        }
    }

}
