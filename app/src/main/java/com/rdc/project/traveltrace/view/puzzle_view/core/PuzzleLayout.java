package com.rdc.project.traveltrace.view.puzzle_view.core;

import android.graphics.RectF;

import java.util.List;

public interface PuzzleLayout {

    void setOuterBounds(RectF bounds);

    void layout();

    int getAreaCount();

    List<Line> getOuterLines();

    List<Line> getLines();

    Area getOuterArea();

    void update();

    void reset();

    Area getArea(int position);

    float width();

    float height();

}
