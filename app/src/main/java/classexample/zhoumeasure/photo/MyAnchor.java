package classexample.zhoumeasure.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Xiaozhou on 2015/8/6.
 */
public class MyAnchor extends View {
    Bitmap m_img;
    Paint m_paint = new Paint();
    float m_radius = 20f, m_x, m_y;

    public MyAnchor(Context context) {
        super(context);
        m_paint.setARGB(255, 0, 150, 180);
        m_paint.setStyle(Paint.Style.FILL);
        m_x = 100f;
        //setPosition(getRootView().getWidth() / 2, getRootView().getHeight() / 2);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("zxz", String.valueOf(event.getX()) + ", " + String.valueOf(event.getY()));
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                    case MotionEvent.ACTION_DOWN:
//                        setPosition(event.getX(), event.getY());
//                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("zxz", "Mve!"+event.getX()+", "+event.getY());
                        setPosition(event.getX(), event.getY());
                        break;
                }
                return true;
            }
        });
    }

    public float getX(){
        return m_x;
    }

    public float getY(){
        return m_y;
    }

    public void setPosition(float x, float y)
    {
        Log.d("zxz", "position changed");
        m_x = x;
        m_y = y;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Log.d("zxz", " redraw");
        canvas.drawCircle(m_x,m_y,m_radius,m_paint);
    }

}
