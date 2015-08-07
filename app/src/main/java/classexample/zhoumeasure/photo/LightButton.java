package classexample.zhoumeasure.photo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Xiaozhou on 2015/8/6.
 */
public class LightButton extends ImageButton {
    boolean m_isActive =false;
    Paint m_paint = new Paint();
    public LightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paint.setColor(Color.BLUE);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate().rotationBy(360).setDuration(500);
                m_isActive = !m_isActive;
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("zxz", "on draw");
        if(m_isActive){
            canvas.drawCircle(getWidth()/2,getHeight()/2, 35 ,m_paint);
            Log.d("zxz","circle");
        }
        super.onDraw(canvas);
    }
}
