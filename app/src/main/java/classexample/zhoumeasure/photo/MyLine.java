package classexample.zhoumeasure.photo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by liyan on 8/6/15.
 */
public class MyLine extends View{

    MyAnchor m_startP,m_endP;
    Paint m_paint = new Paint();

    public MyLine(Context context) {
        super(context);
        m_paint.setARGB(255, 0, 150, 180);
        m_paint.setStrokeWidth(10);
        m_startP = new MyAnchor(context);
        m_endP = new MyAnchor(context);

    }

    public void addAnchors(){
        ((RelativeLayout)getParent()).addView(m_startP);
        ((RelativeLayout)getParent()).addView(m_endP);
    }

    public void setPosition(float sx, float sy, float ex, float ey){
        m_startP.setPosition(sx, sy);
        m_endP.setPosition(ex, ey);
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Log.d("zxz", " redraw");
        canvas.drawLine(m_startP.getX(), m_startP.getY(), m_endP.getX(), m_endP.getY(), m_paint);

    }

}
