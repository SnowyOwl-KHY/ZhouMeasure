package classexample.zhoumeasure.dectector;

import org.opencv.core.Point;

/**
 * Created by Rex on 2015/8/8.
 */
public class Line {
    public Point[] x1;
    public Point[] x2;
    int count;

    public Line(int s) {
        count = s;
        x1 = new Point[s + 1];
        x2 = new Point[s + 1];
    }

    public int getCount(){
        return count;
    }

    public void print() {
        System.out.println("count : " + count);
        for (int x = 0; x < count; x++) {
            System.out.println("x1 : " + x1[x] + "|| x2 : " + x2[x]);

        }
    }
}
