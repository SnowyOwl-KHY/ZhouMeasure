package classexample.zhoumeasure.reference;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

class Utils {

    static final int ANIMATION_TIME = 700;

    public static float getCenterX(View theView) {
        return theView.getX() + theView.getWidth()/2;
    }

    public static float getCenterY(View theView) {
        return theView.getY() + theView.getHeight()/2;
    }

    public static void slideUp(View theView, float delta) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(-delta)
                .setDuration(ANIMATION_TIME);
    }

    public static void slideDown(View theView) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .setDuration(ANIMATION_TIME);
    }

    public static void moveTo(View theView, View theTargetView, float aimAlpha) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .x(Utils.getCenterX(theTargetView) - theView.getWidth() / 2)
                .y(Utils.getCenterY(theTargetView) - theView.getHeight() / 2)
                .alpha(aimAlpha)
                .setDuration(ANIMATION_TIME);
    }

}
