package classexample.zhoumeasure.photo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class PhotoFragment extends Fragment{
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.photo_layout, container, false);
        return rootView;
    }
}
