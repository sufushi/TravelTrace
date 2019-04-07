package com.rdc.project.traveltrace.view.puzzle_view.util;

import android.graphics.PointF;

import com.rdc.project.traveltrace.view.puzzle_view.core.Line;
import com.rdc.project.traveltrace.view.puzzle_view.slant.CrossoverPointF;
import com.rdc.project.traveltrace.view.puzzle_view.slant.SlantArea;
import com.rdc.project.traveltrace.view.puzzle_view.slant.SlantLine;

import java.util.ArrayList;
import java.util.List;

public class SlantUtils {

    private static final PointF A = new PointF();
    private static final PointF B = new PointF();
    private static final PointF C = new PointF();
    private static final PointF D = new PointF();
    private static final PointF AB = new PointF();
    private static final PointF AM = new PointF();
    private static final PointF BC = new PointF();
    private static final PointF BM = new PointF();
    private static final PointF CD = new PointF();
    private static final PointF CM = new PointF();
    private static final PointF DA = new PointF();
    private static final PointF DM = new PointF();

    private SlantUtils() {
        //no instance
    }

    public static List<SlantArea> cutArea(SlantArea area, SlantLine line) {
        List<SlantArea> areas = new ArrayList<>();
        SlantArea area1 = new SlantArea(area);
        SlantArea area2 = new SlantArea(area);

        if (line.direction == Line.Direction.HORIZONTAL) {
            area1.lineBottom = line;
            area1.leftBottom = line.start;
            area1.rightBottom = line.end;

            area2.lineTop = line;
            area2.leftTop = line.start;
            area2.rightTop = line.end;
        } else {
            area1.lineRight = line;
            area1.rightTop = line.start;
            area1.rightBottom = line.end;

            area2.lineLeft = line;
            area2.leftTop = line.start;
            area2.leftBottom = line.end;
        }

        areas.add(area1);
        areas.add(area2);

        return areas;
    }

    public static SlantLine createLine(SlantArea area, Line.Direction direction, float startRadio,
                                       float endRadio) {
        SlantLine line = new SlantLine(direction);

        if (direction == Line.Direction.HORIZONTAL) {
            line.start = getPoint(area.leftTop, area.leftBottom, Line.Direction.VERTICAL, startRadio);
            line.end = getPoint(area.rightTop, area.rightBottom, Line.Direction.VERTICAL, endRadio);

            line.attachLineStart = area.lineLeft;
            line.attachLineEnd = area.lineRight;

            line.upperLine = area.lineBottom;
            line.lowerLine = area.lineTop;
        } else {
            line.start = getPoint(area.leftTop, area.rightTop, Line.Direction.HORIZONTAL, startRadio);
            line.end = getPoint(area.leftBottom, area.rightBottom, Line.Direction.HORIZONTAL, endRadio);

            line.attachLineStart = area.lineTop;
            line.attachLineEnd = area.lineBottom;

            line.upperLine = area.lineRight;
            line.lowerLine = area.lineLeft;
        }

        return line;
    }

    public static List<SlantArea> cutAreaCross(final SlantArea area, final SlantLine horizontal,
                                               final SlantLine vertical, final List<CrossoverPointF> crossoverPoints) {
        List<SlantArea> list = new ArrayList<>();

        CrossoverPointF crossoverPoint = new CrossoverPointF(horizontal, vertical);
        intersectionOfLines(crossoverPoint, horizontal, vertical);
        crossoverPoints.add(crossoverPoint);

        SlantArea one = new SlantArea(area);
        one.lineBottom = horizontal;
        one.lineRight = vertical;

        one.rightTop = vertical.start;
        one.rightBottom = crossoverPoint;
        one.leftBottom = horizontal.start;
        list.add(one);

        SlantArea two = new SlantArea(area);
        two.lineBottom = horizontal;
        two.lineLeft = vertical;

        two.leftTop = vertical.start;
        two.rightBottom = horizontal.end;
        two.leftBottom = crossoverPoint;
        list.add(two);

        SlantArea three = new SlantArea(area);
        three.lineTop = horizontal;
        three.lineRight = vertical;

        three.leftTop = horizontal.start;
        three.rightTop = crossoverPoint;
        three.rightBottom = vertical.end;
        list.add(three);

        SlantArea four = new SlantArea(area);
        four.lineTop = horizontal;
        four.lineLeft = vertical;

        four.leftTop = crossoverPoint;
        four.rightTop = horizontal.end;
        four.leftBottom = vertical.end;
        list.add(four);

        return list;
    }

    public static PointF getPoint(final PointF start, final PointF end,
                                  final Line.Direction direction, float radio) {
        PointF point = new PointF();
        getPoint(point, start, end, direction, radio);
        return point;
    }

    public static void getPoint(final PointF dst, final PointF start, final PointF end,
                                final Line.Direction direction, float radio) {
        float deltaY = Math.abs(start.y - end.y);
        float deltaX = Math.abs(start.x - end.x);
        float maxY = Math.max(start.y, end.y);
        float minY = Math.min(start.y, end.y);
        float maxX = Math.max(start.x, end.x);
        float minX = Math.min(start.x, end.x);
        if (direction == Line.Direction.HORIZONTAL) {
            dst.x = minX + deltaX * radio;
            if (start.y < end.y) {
                dst.y = minY + radio * deltaY;
            } else {
                dst.y = maxY - radio * deltaY;
            }
        } else {
            dst.y = minY + deltaY * radio;
            if (start.x < end.x) {
                dst.x = minX + radio * deltaX;
            } else {
                dst.x = maxX - radio * deltaX;
            }
        }
    }

    // 叉乘
    public static float crossProduct(final PointF a, final PointF b) {
        return a.x * b.y - b.x * a.y;
    }

    /**
     * 判断一个斜线区域是否包含(x,y)点
     *
     * @param area 斜线区域
     * @param x x
     * @param y y
     * @return 是否包含
     */
    public static boolean contains(SlantArea area, float x, float y) {
        AB.x = area.rightTop.x - area.leftTop.x;
        AB.y = area.rightTop.y - area.leftTop.y;

        AM.x = x - area.leftTop.x;
        AM.y = y - area.leftTop.y;

        BC.x = area.rightBottom.x - area.rightTop.x;
        BC.y = area.rightBottom.y - area.rightTop.y;

        BM.x = x - area.rightTop.x;
        BM.y = y - area.rightTop.y;

        CD.x = area.leftBottom.x - area.rightBottom.x;
        CD.y = area.leftBottom.y - area.rightBottom.y;

        CM.x = x - area.rightBottom.x;
        CM.y = y - area.rightBottom.y;

        DA.x = area.leftTop.x - area.leftBottom.x;
        DA.y = area.leftTop.y - area.leftBottom.y;

        DM.x = x - area.leftBottom.x;
        DM.y = y - area.leftBottom.y;

        return crossProduct(AB, AM) > 0
                && crossProduct(BC, BM) > 0
                && crossProduct(CD, CM) > 0
                && crossProduct(DA, DM) > 0;
    }

    public static boolean contains(SlantLine line, float x, float y, float extra) {
        PointF start = line.start;
        PointF end = line.end;
        if (line.direction == Line.Direction.VERTICAL) {
            A.x = start.x - extra;
            A.y = start.y;
            B.x = start.x + extra;
            B.y = start.y;
            C.x = end.x + extra;
            C.y = end.y;
            D.x = end.x - extra;
            D.y = end.y;
        } else {
            A.x = start.x;
            A.y = start.y - extra;
            B.x = end.x;
            B.y = end.y - extra;
            C.x = end.x;
            C.y = end.y + extra;
            D.x = start.x;
            D.y = start.y + extra;
        }

        AB.x = B.x - A.x;
        AB.y = B.y - A.y;

        AM.x = x - A.x;
        AM.y = y - A.y;

        BC.x = C.x - B.x;
        BC.y = C.y - B.y;

        BM.x = x - B.x;
        BM.y = y - B.y;

        CD.x = D.x - C.x;
        CD.y = D.y - C.y;

        CM.x = x - C.x;
        CM.y = y - C.y;

        DA.x = A.x - D.x;
        DA.y = A.y - D.y;

        DM.x = x - D.x;
        DM.y = y - D.y;

        return crossProduct(AB, AM) > 0
                && crossProduct(BC, BM) > 0
                && crossProduct(CD, CM) > 0
                && crossProduct(DA, DM) > 0;
    }

    /**
     * 计算两线的交点
     *
     * @param dst 计算出的交点
     * @param lineOne 线一
     * @param lineTwo 线二
     */
    public static void intersectionOfLines(final PointF dst, final SlantLine lineOne,
                                           final SlantLine lineTwo) {
        if (isParallel(lineOne, lineTwo)) {
            dst.set(0, 0);
            return;
        }

        if (isHorizontalLine(lineOne) && isVerticalLine(lineTwo)) {
            dst.set(lineTwo.start.x, lineOne.start.y);
            return;
        }

        if (isVerticalLine(lineOne) && isHorizontalLine(lineTwo)) {
            dst.set(lineOne.start.x, lineTwo.start.y);
            return;
        }

        if (isHorizontalLine(lineOne) && !isVerticalLine(lineTwo)) {
            float k = calculateSlope(lineTwo);
            float b = calculateVerticalIntercept(lineTwo);

            dst.y = lineOne.start.y;
            dst.x = (dst.y - b) / k;
            return;
        }

        if (isVerticalLine(lineOne) && !isHorizontalLine(lineTwo)) {
            float k = calculateSlope(lineTwo);
            float b = calculateVerticalIntercept(lineTwo);

            dst.x = lineOne.start.x;
            dst.y = k * dst.x + b;
            return;
        }

        if (isHorizontalLine(lineTwo) && !isVerticalLine(lineOne)) {
            float k = calculateSlope(lineOne);
            float b = calculateVerticalIntercept(lineOne);

            dst.y = lineTwo.start.y;
            dst.x = (dst.y - b) / k;
            return;
        }

        if (isVerticalLine(lineTwo) && !isHorizontalLine(lineOne)) {
            float k = calculateSlope(lineOne);
            float b = calculateVerticalIntercept(lineOne);

            dst.x = lineTwo.start.x;
            dst.y = k * dst.x + b;
            return;
        }

        final float k1 = calculateSlope(lineOne);
        final float b1 = calculateVerticalIntercept(lineOne);

        final float k2 = calculateSlope(lineTwo);
        final float b2 = calculateVerticalIntercept(lineTwo);

        dst.x = (b2 - b1) / (k1 - k2);
        dst.y = dst.x * k1 + b1;
    }

    public static boolean isHorizontalLine(SlantLine line) {
        return line.start.y == line.end.y;
    }

    public static boolean isVerticalLine(SlantLine line) {
        return line.start.x == line.end.x;
    }

    /**
     * 判断两条线是否平行
     *
     * @param lineOne 第一条
     * @param lineTwo 第二条
     * @return 是否平行
     */
    public static boolean isParallel(final SlantLine lineOne, final SlantLine lineTwo) {
        return calculateSlope(lineOne) == calculateSlope(lineTwo);
    }

    /**
     * 计算线的斜率
     *
     * @param line 线
     * @return 线的斜率
     */
    public static float calculateSlope(final SlantLine line) {
        if (isHorizontalLine(line)) {
            return 0f;
        } else if (isVerticalLine(line)) {
            return Float.POSITIVE_INFINITY;
        } else {
            return (line.start.y - line.end.y) / (line.start.x - line.end.x);
        }
    }

    /**
     * 计算纵截距
     *
     * @param line 线
     * @return 纵截距
     */
    public static float calculateVerticalIntercept(final SlantLine line) {
        if (isHorizontalLine(line)) {
            return line.start.y;
        } else if (isVerticalLine(line)) {
            return Float.POSITIVE_INFINITY;
        } else {
            float k = calculateSlope(line);
            return line.start.y - k * line.start.x;
        }
    }

}
