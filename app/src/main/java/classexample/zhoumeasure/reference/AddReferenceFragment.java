package classexample.zhoumeasure.reference;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import classexample.zhoumeasure.R;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class AddReferenceFragment extends Fragment {

    View rootView;
    CircularProgressButton mSumbitBtn;
    EditText mNameInput,mDesInput,mLengthInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_new_fragment_layout, container, false);
        mSumbitBtn = (CircularProgressButton)rootView.findViewById(R.id.submitAdd);
        mNameInput = (EditText)rootView.findViewById(R.id.nameInput);
        mDesInput = (EditText)rootView.findViewById(R.id.desInput);
        mLengthInput = (EditText)rootView.findViewById(R.id.lengthInput);
        mSumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSumbitBtn.getProgress() == 0) {
                    if(canSumbit()) {
                        simulateSuccessProgress(mSumbitBtn);
                    }
                    else
                    {
                        Toast.makeText(container.getContext(), "Confirm your input", Toast.LENGTH_SHORT);
                        mSumbitBtn.setProgress(-1);
                    }
                } else {
                    mSumbitBtn.setProgress(0);
                }
            }
        });

        return rootView;
    }
    private boolean canSumbit()
    {
        if(mLengthInput.getText().toString().equals("") ||
                mNameInput.getText().toString().equals("")
                )
        {
            return false;
        }
        else
            return true;
    }
    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        widthAnimation.start();
    }

}
