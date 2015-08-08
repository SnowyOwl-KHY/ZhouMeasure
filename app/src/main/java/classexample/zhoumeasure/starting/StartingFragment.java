package classexample.zhoumeasure.starting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import classexample.zhoumeasure.R;

/**
 * Created by kehanyang on 8/8/15.
 */
public class StartingFragment extends Fragment {

    LinearLayout mStartingLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_starting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mStartingLayout = (LinearLayout) getActivity().findViewById(R.id.startingLayout);
        mStartingLayout.animate().setDuration(2000).alpha(0.0f).setStartDelay(2000);
    }
}
