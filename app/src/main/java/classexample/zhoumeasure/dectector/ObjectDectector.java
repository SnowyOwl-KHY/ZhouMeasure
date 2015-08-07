package classexample.zhoumeasure.dectector;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Rex on 2015/8/8.
 */
public class ObjectDectector {

    static int PARA = 40;
    static String TAG = "TextOpenCV";
    static int MAXMARGIN = 10;
    static double yipilong = 0.1;
    static double mindis = 1000;
    static int maxwidth,maxheight;
    Bitmap bmp;
    static public boolean pd(double x, double y) {
        if (x < MAXMARGIN || y < MAXMARGIN) return true;
        if (maxwidth - x < MAXMARGIN || maxheight - y < MAXMARGIN) return true;
        return false;
    }
    static public double distance(double x1,double y1,double x2,double y2){
        return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
    }
    static public Line getEdge(Bitmap bmp){
        double[] vec;
        int[] pd;
        maxheight = bmp.getHeight();
        maxwidth = bmp.getWidth();
        double x1, x2, x3, x4, y1, y2, y3, y4;
        double mindegree;
        double maxdegree;
        double k1,k2,dis1,dis2,dis3,dis4;
        int count;
        Point minmark = new Point();
        Point maxmark = new Point();
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        Mat lines = new Mat();
        Utils.bitmapToMat(bmp, rgbMat);
        //将彩色图像数据转换为灰度图像数据并存储到grayMat中
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);
        //创建一个灰度图像
        Imgproc.blur(grayMat, grayMat, new Size(5, 5));
        //将矩阵grayMat转换为灰度图像

        Imgproc.Canny(grayMat, grayMat, PARA, PARA * 3, 3, false);

        final Bitmap grayBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(grayMat, grayBmp);

        Imgproc.HoughLinesP(grayMat, lines, 1, Math.PI / 180, maxheight / 10, maxheight / 20, maxheight / 20);
        pd = new int[lines.cols()];

        Canvas tempCanvas = new Canvas(bmp);
        //tempBitmap = bmp;
        tempCanvas.drawBitmap(bmp, 0, 0, null);
        //System.out.println(lines.cols());
        count = 0;
        for (int x = 0; x < lines.cols(); x++) {
            if (pd[x] == -1) continue;

            vec = lines.get(0, x);
            x1 = vec[0];
            y1 = vec[1];
            x2 = vec[2];
            y2 = vec[3];

            if (pd(x1, y1) || pd(x2, y2)) {

                System.out.println(x + " skip");
                pd[x] = -1;
                continue;
            }

            for (int y = 0; y < lines.cols(); y++) {
                if (pd[y] == -1) continue;
                if (x == y) continue;
                vec = lines.get(0, y);
                x3 = vec[0];
                y3 = vec[1];
                x4 = vec[2];
                y4 = vec[3];
                k1 = Math.atan((y2-y1)/(x2-x1));
                k2 = Math.atan((y4-y3)/(x4-x3));
                dis1 = distance(x1, y1, x3, y3);
                dis2 = distance(x1, y1, x4, y4);
                dis3 = distance(x2, y2, x3, y3);
                dis4 = distance(x2,y2,x4,y4);
                if (Math.abs(k1-k2)<yipilong&Math.min(Math.min(dis1, dis2), Math.min(dis3, dis4))<mindis)
                {
                    System.out.println("bijiaola: " + x + " . " + y);
                    if (pd[x] == pd[y] && pd[x] == 0) {
                        count++;
                        pd[x] = count;
                        pd[y] = count;
                    } else if (pd[x] == 0) pd[x] = pd[y];
                    else if (pd[y] == 0) pd[y] = pd[x];
                } else if (pd[x] == 0) {
                    count++;
                    pd[x] = count;
                }

//                        double tmp1 = Math.abs((y2 - y1) * (x4 - x3));
//                        double tmp2 = Math.abs((y4 - y3) * (x2 - x1));
//                        double tmp3 = Math.abs((y2 - y1) * (x1 - x3));
//                        double tmp4 = Math.abs((y1 - y3) * (x2 - x1));
//                        if (Math.abs(tmp1 - tmp2) + Math.abs(tmp4 - tmp3) < (Math.max(tmp1, tmp2) + Math.max(tmp3, tmp4)) * yipilong)//强行认为一条线
//                        {
//                            System.out.println("bijiaola: " + x + " . " + y);
//                            if (pd[x] == pd[y] && pd[x] == 0) {
//                                count++;
//                                pd[x] = count;
//                                pd[y] = count;
//                            } else if (pd[x] == 0) pd[x] = pd[y];
//                            else if (pd[y] == 0) pd[y] = pd[x];
//                        } else if (pd[x] == 0) {
//                            count++;
//                            pd[x] = count;
//                        }
            }
        }
        Point[] sp = new Point[count + 1];
        Point[] dp = new Point[count + 1];
        Line ans = new Line(count);
        for (int i = 1; i <= count; i++) {
            mindegree = 9999;
            maxdegree = -9999;
            minmark = new Point();
            maxmark = new Point();
            System.out.print(i + " : ");
            for (int x = 0; x < lines.cols(); x++)
                if (pd[x] == i) {
                    System.out.print(x + " ");
                    vec = lines.get(0, x);
                    x1 = vec[0];
                    y1 = vec[1];
                    x2 = vec[2];
                    y2 = vec[3];
                    if (y1 / x1 > maxdegree) {
                        maxdegree = y1 / x1;
                        maxmark.x = x1;
                        maxmark.y = y1;
                    }
                    if (y1 / x1 < mindegree) {
                        mindegree = y1 / x1;
                        minmark.x = x1;
                        minmark.y = y1;
                    }
                    if (y2 / x2 > maxdegree) {
                        maxdegree = y2 / x2;
                        maxmark.x = x2;
                        maxmark.y = y2;
                    }
                    if (y2 / x2 < mindegree) {
                        mindegree = y2 / x2;
                        minmark.x = x2;
                        minmark.y = y2;
                    }
                }
            sp[i] = minmark;
            dp[i] = maxmark;
            ans.x1[i-1] = sp[i];
            ans.x2[i-1] = dp[i];
            System.out.println("final:" + sp[i].x + "," + sp[i].y + "||||||" + dp[i].x + "," + dp[i].y + " : " + (dp[i].y - sp[i].y) / (dp[i].x - sp[i].x));
        }

        return ans;//
    }
}
