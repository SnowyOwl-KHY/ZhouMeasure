/**
 * 
 */
package classexample.zhoumeasure.photo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Xiaozhou on 2015/8/6.
 */
public class MyAnchor extends View {

    Bitmap m_img;
    Paint m_paint = new Paint();
    float m_radius = 20f, m_x, m_y;
    boolean m_isDown = false;
    final float kRadiusDiv = 0.5f;
    public MyAnchor(Context context) {
        super(context);
        m_paint.setARGB(255, 0, 150, 180);
        m_paint.setStyle(Paint.Style.FILL);
        m_x = 100f;
        //setPosition(getRootView().getWidth() / 2, getRootView().getHeight() / 2);
    }

    public void setColor(int a, int r, int g, int b){
        m_paint.setARGB(a, r, g, b);
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

    public boolean onThisArea(float x, float y){
        if(     (x < (m_x + m_radius/kRadiusDiv)) &&
                (x > (m_x - m_radius/kRadiusDiv)) &&
                (y < (m_y + m_radius/kRadiusDiv)) &&
                (y > (m_y - m_radius/kRadiusDiv))  )
            return true;
        else
            return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("zxz", " anchorRedraw");
        canvas.drawCircle(m_x,m_y,m_radius,m_paint);
    }

}
