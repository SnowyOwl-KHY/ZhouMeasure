package classexample.zhoumeasure.reference;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.dd.CircularProgressButton;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.romainpiel.shimmer.ShimmerViewHelper;

import classexample.zhoumeasure.R;
import classexample.zhoumeasure.UIAnimation;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class AddReferenceFragment extends Fragment {

    View rootView;
    CircularProgressButton mSubmitBan;
    EditText mNameInput,mDesInput,mLengthInput;
    ShimmerTextView mInfoText;
    Shimmer mShimmer = new Shimmer();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_new_fragment_layout, container, false);
        mShimmer.setRepeatCount(1);
        mSubmitBan = (CircularProgressButton)rootView.findViewById(R.id.submitAdd);
        mNameInput = (EditText)rootView.findViewById(R.id.nameInput);
        mDesInput = (EditText)rootView.findViewById(R.id.desInput);
        mLengthInput = (EditText)rootView.findViewById(R.id.lengthInput);
        mInfoText = (ShimmerTextView)rootView.findViewById(R.id.infoText);

        mInfoText.setVisibility(View.INVISIBLE);
        mSubmitBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubmitBan.getProgress() == 0) {
                    if (canSubmit()) {
                        simulateSuccessProgress(mSubmitBan);
                    } else {
                        mInfoText.setVisibility(View.VISIBLE);
                        mShimmer.start(mInfoText);
                        mInfoText.startAnimation(UIAnimation.getInstance().getFadeIn(1000));
                        //Toast.makeText(container.getContext(), "Confirm your input", Toast.LENGTH_SHORT);
                        mSubmitBan.setProgress(-1);
                    }
                } else {
                    //mInfoText.setVisibility(View.INVISIBLE);
                    mShimmer.cancel();
                    mInfoText.startAnimation(UIAnimation.getInstance().getFadeOut(
                            new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }
                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mInfoText.setVisibility(View.INVISIBLE);
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            }
                    ));

                    mSubmitBan.setProgress(0);
                }
            }
        });

        return rootView;
    }
    private boolean canSubmit()
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
