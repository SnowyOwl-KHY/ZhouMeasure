package classexample.zhoumeasure.reference;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by kehanyang on 8/8/15.
 */
public class Utils {

    static final int ANIMATION_TIME = 700;

    public static void slideUp(View view, float delta) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(-delta)
                .setDuration(ANIMATION_TIME);
    }

    public static void slideDown(View view) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .setDuration(ANIMATION_TIME);
    }

    public static void moveTo(View view, float aimX, float aimY) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .x(aimX)
                .y(aimY)
                .setDuration(ANIMATION_TIME);
    }

    public static void changeAlpha(View view, float aimAlpha) {
        view.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(aimAlpha)
                .setDuration(ANIMATION_TIME);
    }
}
