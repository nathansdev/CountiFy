package com.nathansdev.countify.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * Utility class for animations.
 */
public class AnimatorUtils {
    private static final String VALUE = "NewApi";

    private AnimatorUtils() {

    }

    /**
     * Slide Transition
     *
     * @param rootView Scene root.
     */
    @SuppressLint(VALUE)
    public static void captureTransitionSlideRightToLeft(ViewGroup rootView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionManager.beginDelayedTransition(rootView, new Slide(Gravity.END));
        }
    }
}
