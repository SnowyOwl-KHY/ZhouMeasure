package classexample.zhoumeasure.photo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/5.
 */
public class PhotoFragment extends Fragment {
    View m_rootView;

    //UI Views
    ImageButton m_cameraBtn;
    ImageButton m_pencilBtn;
    RelativeLayout m_drawArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        m_rootView = inflater.inflate(R.layout.fragment_photo, container, false);

        findViews();

        m_cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zxz", m_cameraBtn.getX() + "," + m_cameraBtn.getY());
                //m_cameraBtn.animate().x(m_cameraBtn.getX()).y(m_cameraBtn.getY()+100).setDuration(1000);
                m_cameraBtn.animate().rotationBy(360).setDuration(1000);
//                ((MainActivity) getActivity()).jumpToCameraFragment();
            }
        });

//        m_drawArea = (RelativeLayout)m_rootView.findViewById(R.id.drawArea);
//        MyAnchor m_anchorStart, m_anchorEnd;
//
//        MyLine m_line = new MyLine(m_drawArea.getContext());
//        m_line.setPosition(30, 30, 200, 30);
//        m_drawArea.addView(m_line);
//        m_line.addAnchors();
//        m_drawArea.addView(new MyAnchor(m_drawArea.getContext()));

        return m_rootView;
    }

    private void findViews()
    {
        m_cameraBtn = (ImageButton)m_rootView.findViewById(R.id.cameraBtn);
       // m_pencilBtn = (ImageButton)m_rootView.findViewById(R.id.pencilBtn);
    }

}
