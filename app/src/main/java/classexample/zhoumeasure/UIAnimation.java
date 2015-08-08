package classexample.zhoumeasure;

import android.animation.AnimatorListenerAdapter;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Xiaozhou on 2015/8/7.
 */
public class UIAnimation {
    private static UIAnimation instance =null;
    private final Animation fadeIn = new AlphaAnimation(0, 1);
    private final Animation fadeOut = new AlphaAnimation(1, 0);

    public static UIAnimation getInstance()
    {
        if(instance == null)
        {
            instance = new UIAnimation();
         }
        return instance;
    }

    UIAnimation()
    {
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(600);

    }

    public Animation getFadeIn(int durationTime)
    {
        fadeIn.setDuration(durationTime);
        return fadeIn;
    }
    public Animation getFadeOut()
    {
        return fadeOut;
    }
    public Animation getFadeOut(AnimationListener listener)
    {
        fadeOut.setAnimationListener(listener);
        return fadeOut;
    }
}
