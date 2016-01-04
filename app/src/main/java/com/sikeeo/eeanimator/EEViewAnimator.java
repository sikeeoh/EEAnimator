package com.sikeeo.eeanimator;

import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deiphoks on 2016. 1. 4..
 */
public class EEViewAnimator {

    List<EEAnimationBuilder> eeAnimationList = new ArrayList<>();
    Long duration = 0L;
    Long startDelay = 0L;
    Interpolator interpolator = null;

    AnimatorSet animatorSet;
    View waitView = null;

    EEAnimationListener.Start startListener;
    EEAnimationListener.Stop stopListener;

    EEViewAnimator prevAnimator = null;
    EEViewAnimator nextAnimator = null;

    public static EEAnimationBuilder animate(View... views) {
        EEViewAnimator eeViewAnimator = new EEViewAnimator();
        return eeViewAnimator.addAnimationBuilder(views);
    }

    public EEAnimationBuilder addAnimationBuilder(View... views) {
        EEAnimationBuilder eeAnimationBuilder = new EEAnimationBuilder(this, views);
        eeAnimationList.add(eeAnimationBuilder);
        return eeAnimationBuilder;
    }

}
