package uk.co.amlcurran.viewcontroller;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.Build;
import android.view.animation.AnimationUtils;

public class AnimateUtils {
    static int getInterpolator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.R.interpolator.fast_out_linear_in;
        } else {
            return android.R.interpolator.decelerate_cubic;
        }
    }

    public static TimeInterpolator getInterpolator(Context context) {
        return AnimationUtils.loadInterpolator(context, getInterpolator());
    }
}
