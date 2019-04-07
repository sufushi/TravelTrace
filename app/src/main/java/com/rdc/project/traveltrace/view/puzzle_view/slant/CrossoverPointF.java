package com.rdc.project.traveltrace.view.puzzle_view.slant;

import android.graphics.PointF;

import com.rdc.project.traveltrace.view.puzzle_view.util.SlantUtils;

public class CrossoverPointF extends PointF {

    private final SlantLine horizontal;
    private final SlantLine vertical;

    public CrossoverPointF(SlantLine horizontal, SlantLine vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public void update() {
        SlantUtils.intersectionOfLines(this, horizontal, vertical);
    }

}
