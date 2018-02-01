package com.wangyuelin.fruit.utils;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangyuelin on 2017/6/22.
 */

public class SuperOvalUtil {

    private static final float N = 2.5f;

    /**
     * 计算超椭圆的取样点
     * 超椭圆 其中n、a及b为正数。解会是一个在−a ≤ x ≤ +a及−b ≤ y ≤ +b长方形内的封闭曲线，参数a及b称为曲线的半直径（semi-diameters）。
     *
     * @param a
     * @param b
     */
    private static List<Point> caculateSuperEllipsePoint(float a, float b) {
        //既然是堆成的，那只要计算一个象限的坐标即可知道其余三个象限的坐标
        //取x 由 0 ~ a计算 y
        if (a <= 0 || b <= 0 || a < 1) {
            return null;
        }
        int min = (int) Math.min(a, b);
        a = b = min;//超椭圆，x和y相等

        ArrayList<Point> quadrantOneList = new ArrayList<>();
        ArrayList<Point> quadrantTwoList = new ArrayList<>();
        ArrayList<Point> quadrantThreeList = new ArrayList<>();
        ArrayList<Point> quadrantFourList = new ArrayList<>();
        ArrayList<Point> pointList = new ArrayList<>();

        Point point;
        for (int i = 0; i <= a; i++) {
            point = new Point(i, funSuperEllipse(i, N, a, b));
            quadrantOneList.add(point);//其他三象限的坐标都能由它推算出来，所以其他不用计算了
            quadrantTwoList.add(new Point(point.x, -point.y));
            quadrantThreeList.add(new Point(-point.x, -point.y));
            quadrantFourList.add(new Point(-point.x, point.y));
        }
        pointList.addAll(quadrantOneList);
        Collections.reverse(quadrantTwoList);
        pointList.addAll(quadrantTwoList);
        pointList.addAll(quadrantThreeList);
        Collections.reverse(quadrantFourList);
        pointList.addAll(quadrantFourList);
        return pointList;
    }

    /**
     * 超椭圆的计算公式，由坐标x计算坐标y
     *
     * @param x
     * @return
     */
    private static float funSuperEllipse(float x, float n, float a, float b) {
        float temp = (float) ((1 - Math.pow(x / a, n)) * Math.pow(b, n));
        return (float) sqrt(temp, n);
    }

    /**
     * 据坐标绘制平滑的曲线
     */
    public static Path caculateSuperEllipsePath(float a, float b) {
        if (a <= 0 || b <= 0 || a < 1) {
            return null;
        }
        List<Point> points = caculateSuperEllipsePoint(a, b);
        if (points == null) {
            return null;
        }
        Path path = new Path();
        final Point originPoint = points.get(0);
        path.reset();
        path.moveTo(originPoint.x, originPoint.y);

        final int size = points.size();
        for (int i = 1; i < size; i++) {
            Point point = points.get(i);
            path.lineTo(point.x, point.y);
        }
        path.lineTo(originPoint.x, originPoint.y);
        points.clear();

        return path;
    }

    private static class Point implements Cloneable {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    /**
     * 开n次方
     *
     * @param d
     * @param n
     * @return
     */
    private static double sqrt(float d, float n) {
        n = 1 / n;
        return Math.pow(d, n);
    }
}
