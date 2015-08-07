package classexample.zhoumeasure.photo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/5.
 */
public class PhotoFragment extends Fragment {

    View m_rootView;
    double m_refLength = 15, m_tarLength = 0;
    TextView m_refLenView, m_tarLenView;
    MyLine m_line;
    Bitmap m_bitmap = null;

    // UI Views
//    ImageButton m_pencilBtn;
//    ImageButton m_rulerBtn;
    RelativeLayout m_drawArea;

    public static PhotoFragment instance = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        instance = this;

        m_rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        findViews();

        // set background
        m_drawArea = (RelativeLayout) m_rootView.findViewById(R.id.drawArea);
        if (m_bitmap != null) {
            Drawable m_tempDr = new BitmapDrawable(m_bitmap);
            m_drawArea.setBackground(m_tempDr);
        }

        // get width
        WindowManager wm = (WindowManager) m_drawArea.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated

        Log.d("ly", "" + width);

        m_refLenView.setText("Ref: " + m_refLength + "cm");
        m_tarLenView.setText("Tar: " + "Null");

        m_line = new MyLine(m_drawArea.getContext());
        m_line.setPosition(width / 3);
        m_drawArea.addView(m_line);
        m_line.addAnchors();
        return m_rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void setResult() {
        Log.e("blabla", "ok");
        m_tarLength = m_line.calPropotion() * m_refLength;
        m_refLenView.setText("Ref: " + m_refLength + "cm");
        DecimalFormat format = new DecimalFormat("#.0");
        m_tarLenView.setText("Tar: " + format.format(m_tarLength) + "cm");
    }

    public void setBitmap(Bitmap inBitmap) {
        m_bitmap = inBitmap;
    }

    public void setRefLength(double inRefLength) {
        m_refLength = inRefLength;
        setResult();
    }

    private void findViews() {
        m_refLenView = (TextView) m_rootView.findViewById(R.id.refLen);
        m_tarLenView = (TextView) m_rootView.findViewById(R.id.tarLen);
//        m_pencilBtn = (ImageButton)m_rootView.findViewById(R.id.pencilBtn);
//        m_rulerBtn = (ImageButton)m_rootView.findViewById(R.id.rulerBtn);
    }

}
