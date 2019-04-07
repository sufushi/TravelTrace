package com.rdc.project.traveltrace.view.puzzle_view.slant;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;
import com.rdc.project.traveltrace.view.puzzle_view.util.SlantUtils;

import android.graphics.PointF;

import static com.rdc.project.traveltrace.view.puzzle_view.util.SlantUtils.intersectionOfLines;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SlantLine implements Line {

    public PointF start;
    public PointF end;

    // 移动前的点
    public PointF previousStart = new PointF();
    public PointF previousEnd = new PointF();

    public final Line.Direction direction;

    public SlantLine attachLineStart;
    public SlantLine attachLineEnd;

    public Line upperLine;
    public Line lowerLine;

    public SlantLine(Line.Direction direction) {
        this.direction = direction;
    }

    public SlantLine(PointF start, PointF end, Line.Direction direction) {
        this.start = start;
        this.end = end;
        this.direction = direction;
    }

    public float length() {
        return (float) sqrt(pow(end.x - start.x, 2) + pow(end.y - start.y, 2));
    }

    @Override
    public PointF startPoint() {
        return start;
    }

    @Override
    public PointF endPoint() {
        return end;
    }

    @Override
    public Line lowerLine() {
        return lowerLine;
    }

    @Override
    public Line upperLine() {
        return upperLine;
    }

    @Override
    public Line attachStartLine() {
        return attachLineStart;
    }

    @Override
    public Line attachEndLine() {
        return attachLineEnd;
    }

    @Override
    public void setLowerLine(Line lowerLine) {
        this.lowerLine = lowerLine;
    }

    @Override
    public void setUpperLine(Line upperLine) {
        this.upperLine = upperLine;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public float slope() {
        return SlantUtils.calculateSlope(this);
    }

    public boolean contains(float x, float y, float extra) {
        return SlantUtils.contains(this, x, y, extra);
    }

    @Override
    public boolean move(float offset, float extra) {
        if (direction == Line.Direction.HORIZONTAL) {
            if (previousStart.y + offset < lowerLine.maxY() + extra
                    || previousStart.y + offset > upperLine.minY() - extra
                    || previousEnd.y + offset < lowerLine.maxY() + extra
                    || previousEnd.y + offset > upperLine.minY() - extra) {
                return false;
            }

            start.y = previousStart.y + offset;
            end.y = previousEnd.y + offset;
        } else {
            if (previousStart.x + offset < lowerLine.maxX() + extra
                    || previousStart.x + offset > upperLine.minX() - extra
                    || previousEnd.x + offset < lowerLine.maxX() + extra
                    || previousEnd.x + offset > upperLine.minX() - extra) {
                return false;
            }

            start.x = previousStart.x + offset;
            end.x = previousEnd.x + offset;
        }

        return true;
    }

    @Override
    public void prepareMove() {
        previousStart.set(start);
        previousEnd.set(end);
    }

    @Override
    public void update(float layoutWidth, float layoutHeight) {
        intersectionOfLines(start, this, attachLineStart);
        start.x = max(start.x, 0);
        start.x = min(start.x, layoutWidth);
        start.y = max(start.y, 0);
        start.y = min(start.y, layoutHeight);
        intersectionOfLines(end, this, attachLineEnd);
        end.x = max(end.x, 0);
        end.x = min(end.x, layoutWidth);
        end.y = max(end.y, 0);
        end.y = min(end.y, layoutHeight);
    }

    @Override
    public float minX() {
        return min(start.x, end.x);
    }

    @Override
    public float maxX() {
        return max(start.x, end.x);
    }

    @Override
    public float minY() {
        return min(start.y, end.y);
    }

    @Override
    public float maxY() {
        return max(start.y, end.y);
    }

    @Override
    public void offset(float x, float y) {
        start.offset(x, y);
        end.offset(x, y);
    }

    @Override
    public String toString() {
        return "start --> " + start.toString() + ",end --> " + end.toString();
    }

}
