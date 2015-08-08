package classexample.zhoumeasure.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import classexample.zhoumeasure.detector.Line;
import classexample.zhoumeasure.detector.ObjectDectector;

/**
 * Created by liyan on 8/6/15.
 */
public class MyLine extends View{

    final int selectD = 30;
    MyAnchor[] m_anchor = new MyAnchor[4];
    Paint m_paint = new Paint();
    int m_mode = 0;
    Bitmap m_bitmap;
    Line m_lines;
    boolean isRefSelected = false;
    boolean isTarSelected = false;
    double m_refDist = 0;
    double m_tarDist = 0;

    public MyLine(Context context) {

        super(context);
        m_paint.setStrokeWidth(10);

        for(int i=0; i<4; i++)
        {
            m_anchor[i] = new MyAnchor(context);
            if(i<2) {
                m_anchor[i].setColor(255, 151, 203, 0);
            }
            else {
                m_anchor[i].setColor(180, 2, 143, 245);
            }
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("zxz", String.valueOf(event.getX()) + ", " + String.valueOf(event.getY()));
                if(m_mode == 1) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            for (int i = 0; i < 4; i++) {
                                if (m_anchor[i].onThisArea(event.getX(), event.getY())) {
                                    m_anchor[i].m_isDown = true;
                                    break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.d("zxz", "Move!" + event.getX() + ", " + event.getY());
                            for (int i = 0; i < 4; i++) {
                                if (m_anchor[i].m_isDown) {
                                    m_anchor[i].setPosition(event.getX(), event.getY());
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            for (int i = 0; i < 4; i++) {
                                m_anchor[i].m_isDown = false;
                            }
                            break;
                    }
                    lineRedraw();
                    PhotoFragment.instance.setResult();
                }
                else if(m_mode == 2){
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            for (int i = 0; i < m_lines.getCount(); i++) {
                                if(event.getX() < (m_lines.x1[i].x + m_lines.x2[i].x)/2+selectD &&
                                        event.getY() < (m_lines.x1[i].y + m_lines.x2[i].y)/2+selectD &&
                                        event.getX() > (m_lines.x1[i].x + m_lines.x2[i].x)/2-selectD &&
                                        event.getY() < (m_lines.x1[i].y + m_lines.x2[i].y)/2+selectD ) {
                                    if (!isRefSelected && !isTarSelected) {
                                        isRefSelected = true;
                                        m_refDist = Math.sqrt((m_lines.x1[i].x - m_lines.x2[i].x) * (m_lines.x1[i].x - m_lines.x2[i].x) +
                                                (m_lines.x1[i].y - m_lines.x2[i].y) * (m_lines.x1[i].y - m_lines.x2[i].y));
                                        PhotoFragment.instance.m_refLenView.setVisibility(View.VISIBLE);
                                        break;
                                    }
                                    else if(!isTarSelected && isRefSelected){
                                        isTarSelected = true;
                                        m_tarDist = Math.sqrt((m_lines.x1[i].x - m_lines.x2[i].x) * (m_lines.x1[i].x - m_lines.x2[i].x) +
                                                (m_lines.x1[i].y - m_lines.x2[i].y) * (m_lines.x1[i].y - m_lines.x2[i].y));
                                        PhotoFragment.instance.m_tarLenView.setVisibility(View.VISIBLE);
                                        break;
                                    }
                                }
                            }
                            break;
                    }
                }
                return true;
            }
        });

    }

    public void setClear()
    {
        isTarSelected = false;
        isRefSelected = false;
        m_refDist = 0;
        m_tarDist = 0;
        PhotoFragment.instance.m_tarLenView.setVisibility(View.GONE);
        PhotoFragment.instance.m_refLenView.setVisibility(View.GONE);
    }

    public void setBitmap(Bitmap inBitmap){
        m_bitmap = inBitmap;
    }

    public double calPropotion(){
        double propotion = 0;
        double refDist = 0;
        double tarDist = 0;
        refDist = Math.sqrt(
                (m_anchor[1].getY() - m_anchor[0].getY()) * (m_anchor[1].getY() - m_anchor[0].getY()) +
                        (m_anchor[1].getX() - m_anchor[0].getX()) * (m_anchor[1].getX() - m_anchor[0].getX()) );
        tarDist = Math.sqrt(
                (m_anchor[3].getY() - m_anchor[2].getY()) * (m_anchor[3].getY() - m_anchor[2].getY()) +
                        (m_anchor[3].getX() - m_anchor[2].getX()) * (m_anchor[3].getX() - m_anchor[2].getX()) );
        propotion = tarDist/refDist;
        if(m_mode == 2)
        {
            return m_tarDist/m_refDist;
        }
        return propotion;
    }

    public void setPosition(int d)
    {
        int margin = 60;
        for(int i=0; i<4; i++)
            m_anchor[i].setPosition(margin + (d-margin*2/3) * i, 100);
    }

    private void lineRedraw() {
        this.invalidate();
    }

    public void addAnchors(){
        for(int i=0; i<4; i++)
            ((RelativeLayout)getParent()).addView(m_anchor[i]);
    }

    public void setMode(int mode){
        m_mode = mode;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("zxz", " lineredraw");
        if(m_mode == 0){
            Log.d("ly", "deep dark fantasy0");
            for(int i=0; i<4; i++){
                m_anchor[i].setVisibility(View.GONE);
            }
            PhotoFragment.instance.m_refLenView.setVisibility(View.GONE);
            PhotoFragment.instance.m_tarLenView.setVisibility(View.GONE);
        }
        else if(m_mode == 1) {
            Log.d("ly", "deep dark fantasy1");
            for(int i=0; i<4; i++){
                m_anchor[i].setVisibility(View.VISIBLE);
            }
            PhotoFragment.instance.m_refLenView.setVisibility(View.VISIBLE);
            PhotoFragment.instance.m_tarLenView.setVisibility(View.VISIBLE);
            m_paint.setARGB(180, 151, 203, 0);
            canvas.drawLine(m_anchor[0].getX(), m_anchor[0].getY(), m_anchor[1].getX(), m_anchor[1].getY(), m_paint);
            m_paint.setARGB(180, 2, 143, 245);
            canvas.drawLine(m_anchor[2].getX(), m_anchor[2].getY(), m_anchor[3].getX(), m_anchor[3].getY(), m_paint);
        }
        else if(m_mode == 2){
            m_paint.setColor(Color.RED);
            Log.d("ly", "deep dark fantasy2");
            for(int i=0; i<4; i++){
                m_anchor[i].setVisibility(View.GONE);
            }
            PhotoFragment.instance.m_refLenView.setVisibility(View.GONE);
            PhotoFragment.instance.m_tarLenView.setVisibility(View.GONE);
            m_lines = ObjectDectector.getEdge(m_bitmap);
            Log.d("ly", m_bitmap.getWidth() + " " + m_bitmap.getHeight());
            Log.d("ly", ObjectDectector.getWidth()+" "+ObjectDectector.getHeight());
            for(int i=0; i<m_lines.getCount(); i++){
                canvas.drawLine((float)m_lines.x1[i].x, (float)m_lines.x1[i].y, (float)m_lines.x2[i].x, (float)m_lines.x2[i].y, m_paint);
            }
        }
    }

}
