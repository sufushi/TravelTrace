package com.rdc.project.traveltrace.view.puzzle_view.straight;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.rdc.project.traveltrace.view.puzzle_view.core.Area;
import com.rdc.project.traveltrace.view.puzzle_view.core.Line;
import com.rdc.project.traveltrace.view.puzzle_view.core.PuzzleLayout;

import static com.rdc.project.traveltrace.view.puzzle_view.util.StraightUtils.createLine;
import static com.rdc.project.traveltrace.view.puzzle_view.util.StraightUtils.cutArea;
import static com.rdc.project.traveltrace.view.puzzle_view.util.StraightUtils.cutAreaCross;

public abstract class StraightPuzzleLayout implements PuzzleLayout {

    private static final String TAG = "StraightPuzzleLayout";
    private StraightArea outerArea;

    private List<StraightArea> areas = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private List<Line> outerLines = new ArrayList<>(4);

    private Comparator<StraightArea> areaComparator = new StraightArea.AreaComparator();

    public StraightPuzzleLayout() {

    }

    public StraightPuzzleLayout(RectF baseRect) {
        setOuterBounds(baseRect);
    }

    @Override
    public void setOuterBounds(RectF bounds) {
        PointF one = new PointF(bounds.left, bounds.top);
        PointF two = new PointF(bounds.right, bounds.top);
        PointF three = new PointF(bounds.left, bounds.bottom);
        PointF four = new PointF(bounds.right, bounds.bottom);

        StraightLine lineLeft = new StraightLine(one, three);
        StraightLine lineTop = new StraightLine(one, two);
        StraightLine lineRight = new StraightLine(two, four);
        StraightLine lineBottom = new StraightLine(three, four);

        outerLines.clear();

        outerLines.add(lineLeft);
        outerLines.add(lineTop);
        outerLines.add(lineRight);
        outerLines.add(lineBottom);

        outerArea = new StraightArea(bounds);

        areas.clear();
        areas.add(outerArea);
    }

    @Override
    public abstract void layout();

    @Override
    public int getAreaCount() {
        return areas.size();
    }

    @Override
    public List<Line> getOuterLines() {
        return outerLines;
    }

    @Override
    public List<Line> getLines() {
        return lines;
    }

    @Override
    public void update() {
        for (Line line : lines) {
            line.update(width(), height());
        }
    }

    @Override
    public float width() {
        return outerArea == null ? 0 : outerArea.width();
    }

    @Override
    public float height() {
        return outerArea == null ? 0 : outerArea.height();
    }

    @Override
    public void reset() {
        lines.clear();
        areas.clear();
        areas.add(outerArea);
    }

    @Override
    public Area getArea(int position) {
        return areas.get(position);
    }

    @Override
    public StraightArea getOuterArea() {
        return outerArea;
    }

    protected List<StraightArea> addLine(int position, Line.Direction direction, float ratio) {
        StraightArea area = areas.get(position);
        return addLine(area, direction, ratio);
    }

    private List<StraightArea> addLine(StraightArea area, Line.Direction direction, float ratio) {
        areas.remove(area);
        StraightLine line = createLine(area, direction, ratio);
        lines.add(line);

        List<StraightArea> increasedArea = cutArea(area, line);
        areas.addAll(increasedArea);

        updateLineLimit();
        sortAreas();

        return increasedArea;
    }

    protected void cutBorderEqualPart(int position, int part, Line.Direction direction) {
        StraightArea temp = areas.get(position);
        for (int i = part; i > 1; i--) {
            temp = addLine(temp, direction, (float) (i - 1) / i).get(0);
        }
    }

    protected List<StraightArea> addCross(int position, float radio) {
        return addCross(position, radio, radio);
    }

    protected List<StraightArea> addCross(int position, float horizontalRadio, float verticalRadio) {
        StraightArea area = areas.get(position);
        areas.remove(area);
        StraightLine horizontal = createLine(area, Line.Direction.HORIZONTAL, horizontalRadio);
        StraightLine vertical = createLine(area, Line.Direction.VERTICAL, verticalRadio);
        lines.add(horizontal);
        lines.add(vertical);

        List<StraightArea> newAreas = cutAreaCross(area, horizontal, vertical);
        areas.addAll(newAreas);

        updateLineLimit();
        sortAreas();

        return newAreas;
    }

    protected List<StraightArea> cutBorderEqualPart(int position, int hSize, int vSize) {
        if ((hSize + 1) * (vSize + 1) > 9) {
            Log.e(TAG, "cutBorderEqualPart: the size can not be so great");
            return null;
        }

        StraightArea area = areas.get(position);
        areas.remove(area);
        List<StraightArea> newAreas = new ArrayList<>();
        switch (hSize) {
            case 1:
                switch (vSize) {
                    case 1:
                        newAreas.addAll(addCross(position, 1f / 2));
                        break;
                    case 2:
                        StraightLine l1 = createLine(area, Line.Direction.VERTICAL, 1f / 3);
                        StraightLine l2 = createLine(area, Line.Direction.VERTICAL, 2f / 3);
                        StraightLine l3 = createLine(area, Line.Direction.HORIZONTAL, 1f / 2);

                        lines.add(l1);
                        lines.add(l2);
                        lines.add(l3);

                        newAreas.addAll(cutArea(area, l1, l2, l3, Line.Direction.VERTICAL));
                        break;

                    case 3:
                        StraightLine ll1 = createLine(area, Line.Direction.VERTICAL, 1f / 4);
                        StraightLine ll2 = createLine(area, Line.Direction.VERTICAL, 2f / 4);
                        StraightLine ll3 = createLine(area, Line.Direction.VERTICAL, 3f / 4);
                        StraightLine ll4 = createLine(area, Line.Direction.HORIZONTAL, 1f / 2);

                        lines.add(ll1);
                        lines.add(ll2);
                        lines.add(ll3);
                        lines.add(ll4);

                        newAreas.addAll(cutArea(area, ll1, ll2, ll3, ll4, Line.Direction.VERTICAL));

                        break;
                }
                break;

            case 2:
                switch (vSize) {
                    case 1:
                        StraightLine l1 = createLine(area, Line.Direction.HORIZONTAL, 1f / 3);
                        StraightLine l2 = createLine(area, Line.Direction.HORIZONTAL, 2f / 3);
                        StraightLine l3 = createLine(area, Line.Direction.VERTICAL, 1f / 2);

                        lines.add(l1);
                        lines.add(l2);
                        lines.add(l3);

                        newAreas.addAll(cutArea(area, l1, l2, l3, Line.Direction.HORIZONTAL));

                        break;
                    case 2:
                        StraightLine ll1 = createLine(area, Line.Direction.HORIZONTAL, 1f / 3);
                        StraightLine ll2 = createLine(area, Line.Direction.HORIZONTAL, 2f / 3);
                        StraightLine ll3 = createLine(area, Line.Direction.VERTICAL, 1f / 3);
                        StraightLine ll4 = createLine(area, Line.Direction.VERTICAL, 2f / 3);

                        lines.add(ll1);
                        lines.add(ll2);
                        lines.add(ll3);
                        lines.add(ll4);

                        newAreas.addAll(cutArea(area, ll1, ll2, ll3, ll4));
                        break;
                }
                break;

            case 3:
                switch (vSize) {
                    case 1:
                        StraightLine ll1 = createLine(area, Line.Direction.HORIZONTAL, 1f / 4);
                        StraightLine ll2 = createLine(area, Line.Direction.HORIZONTAL, 2f / 4);
                        StraightLine ll3 = createLine(area, Line.Direction.HORIZONTAL, 3f / 4);
                        StraightLine ll4 = createLine(area, Line.Direction.VERTICAL, 1f / 2);

                        lines.add(ll1);
                        lines.add(ll2);
                        lines.add(ll3);
                        lines.add(ll4);

                        newAreas.addAll(cutArea(area, ll1, ll2, ll3, ll4, Line.Direction.HORIZONTAL));
                        break;
                }
        }

        areas.addAll(newAreas);

        updateLineLimit();
        sortAreas();

        return newAreas;
    }

    protected List<StraightArea> cutSpiral(int position) {
        StraightArea area = areas.get(position);
        areas.remove(area);
        List<StraightArea> newAreas = new ArrayList<>();

        float width = area.width();
        float height = area.height();

        PointF one = new PointF(0, height / 3);
        PointF two = new PointF(width / 3 * 2, 0);
        PointF three = new PointF(width, height / 3 * 2);
        PointF four = new PointF(width / 3, height);
        PointF five = new PointF(width / 3, height / 3);
        PointF six = new PointF(width / 3 * 2, height / 3);
        PointF seven = new PointF(width / 3 * 2, height / 3 * 2);
        PointF eight = new PointF(width / 3, height / 3 * 2);

        StraightLine l1 = new StraightLine(one, six);
        StraightLine l2 = new StraightLine(two, seven);
        StraightLine l3 = new StraightLine(eight, three);
        StraightLine l4 = new StraightLine(five, four);

        l1.setAttachLineStart(area.lineLeft);
        l1.setAttachLineEnd(l2);
        l1.setUpperLine(area.lineTop);
        l1.setLowerLine(l3);

        l2.setAttachLineStart(area.lineTop);
        l2.setAttachLineEnd(l3);
        l2.setUpperLine(area.lineRight);
        l2.setLowerLine(l4);

        l3.setAttachLineStart(l4);
        l3.setAttachLineEnd(area.lineRight);
        l3.setUpperLine(l1);
        l3.setLowerLine(area.lineBottom);

        l4.setAttachLineStart(l1);
        l4.setAttachLineEnd(area.lineBottom);
        l4.setUpperLine(l2);
        l4.setLowerLine(area.lineLeft);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);

        StraightArea b1 = new StraightArea(area);
        b1.lineRight = l2;
        b1.lineBottom = l1;
        newAreas.add(b1);

        StraightArea b2 = new StraightArea(area);
        b2.lineLeft = l2;
        b2.lineBottom = l3;
        newAreas.add(b2);

        StraightArea b3 = new StraightArea(area);
        b3.lineRight = l4;
        b3.lineTop = l1;
        newAreas.add(b3);

        StraightArea b4 = new StraightArea(area);
        b4.lineTop = l1;
        b4.lineRight = l2;
        b4.lineLeft = l4;
        b4.lineBottom = l3;
        newAreas.add(b4);

        StraightArea b5 = new StraightArea(area);
        b5.lineLeft = l4;
        b5.lineTop = l3;
        newAreas.add(b5);

        areas.addAll(newAreas);

        updateLineLimit();
        sortAreas();

        return newAreas;
    }

    private void sortAreas() {
        Collections.sort(areas, areaComparator);
    }

    private void updateLineLimit() {
        for (Line line : lines) {
            updateUpperLine(line);
            updateLowerLine(line);
        }
    }

    private void updateLowerLine(final Line line) {
        for (Line l : lines) {
            if (l == line) {
                continue;
            }

            if (l.direction() != line.direction()) {
                continue;
            }

            if (l.attachStartLine() != line.attachStartLine()
                    || l.attachEndLine() != line.attachEndLine()) {
                continue;
            }

            if (l.direction() == Line.Direction.HORIZONTAL) {
                if (l.minY() > line.lowerLine().maxY() && l.maxY() < line.minY()) {
                    line.setLowerLine(l);
                }
            } else {
                if (l.minX() > line.lowerLine().maxX() && l.maxX() < line.minX()) {
                    line.setLowerLine(l);
                }
            }
        }
    }

    private void updateUpperLine(final Line line) {
        for (Line l : lines) {
            if (l == line) {
                continue;
            }

            if (l.direction() != line.direction()) {
                continue;
            }

            if (l.attachStartLine() != line.attachStartLine()
                    || l.attachEndLine() != line.attachEndLine()) {
                continue;
            }

            if (l.direction() == Line.Direction.HORIZONTAL) {
                if (l.maxY() < line.upperLine().minY() && l.minY() > line.maxY()) {
                    line.setUpperLine(l);
                }
            } else {
                if (l.maxX() < line.upperLine().minX() && l.minX() > line.maxX()) {
                    line.setUpperLine(l);
                }
            }
        }
    }

}
