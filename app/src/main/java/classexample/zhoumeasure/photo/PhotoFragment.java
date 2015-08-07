package classexample.zhoumeasure.photo;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pnikosis.materialishprogress.ProgressWheel;

import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class PhotoFragment extends Fragment{
    View rootView;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.photo_layout, container, false);
        mContext = container.getContext();
        ProgressWheel wheel = new ProgressWheel(container.getContext());
        wheel.setBarColor(Color.BLUE);
        container.addView(wheel);

        return rootView;
    }


}

