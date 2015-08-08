package classexample.zhoumeasure.photo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by liyan on 8/6/15.
 */
public class MyLine extends View{

    MyAnchor[] m_anchor = new MyAnchor[4];
    Paint m_paint = new Paint();
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
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        for(int i=0; i<4; i++)
                        {
                            if(m_anchor[i].onThisArea(event.getX(), event.getY()))
                            {
                                m_anchor[i].m_isDown = true;
                                break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("zxz", "Move!" + event.getX() + ", " + event.getY());
                        for(int i=0; i<4; i++)
                        {
                            if(m_anchor[i].m_isDown)
                            {
                                m_anchor[i].setPosition(event.getX(), event.getY());
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        for(int i=0; i<4; i++)
                        {
                            m_anchor[i].m_isDown = false;
                        }
                        break;
                }
                lineRedraw();
                PhotoFragment.instance.setResult();
                return true;
            }
        });

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
        return propotion;
    }

    public void setPosition(int d)
    {
        int margin = 60;
        for(int i=0; i<4; i++)
            m_anchor[i].setPosition(margin + (d-margin*2/3) * i, 150);
    }

    private void lineRedraw() {
        this.invalidate();
    }

    public void addAnchors(){
        for(int i=0; i<4; i++)
            ((RelativeLayout)getParent()).addView(m_anchor[i]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m_paint.setARGB(180, 151, 203, 0);
        canvas.drawLine(m_anchor[0].getX(), m_anchor[0].getY(), m_anchor[1].getX(), m_anchor[1].getY(), m_paint);
        m_paint.setARGB(180, 2, 143, 245);
        canvas.drawLine(m_anchor[2].getX(), m_anchor[2].getY(), m_anchor[3].getX(), m_anchor[3].getY(), m_paint);
    }

}
