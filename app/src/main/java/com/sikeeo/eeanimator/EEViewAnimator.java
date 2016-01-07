package com.sikeeo.eeanimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sikeeo on 2016. 1. 4..
 */
public class EEViewAnimator {

    List<EEAnimationBuilder> eeAnimationList = new ArrayList<>();
    Long duration = 0L;
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

    public EEAnimationBuilder nextAnimate(View... views) {
        EEViewAnimator nextViewAnimator = new EEViewAnimator();
        this.nextAnimator = nextViewAnimator;
        nextViewAnimator.prevAnimator = this;
        return nextAnimator.addAnimationBuilder(views);
    }

    public EEAnimationBuilder addAnimationBuilder(View... views) {
        EEAnimationBuilder eeAnimationBuilder = new EEAnimationBuilder(this, views);
        eeAnimationList.add(eeAnimationBuilder);
        return eeAnimationBuilder;
    }

    public AnimatorSet createAnimatorSet() {
        List<Animator> animators = new ArrayList<>();
        for (EEAnimationBuilder eeAnimationBuilder : eeAnimationList) {
            animators.addAll(eeAnimationBuilder.createAnimators());
        }

        for (EEAnimationBuilder eeAnimationBuilder : eeAnimationList) {
            if (eeAnimationBuilder.isWaitForView()) {
                waitView = eeAnimationBuilder.getView();
                break;
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animators);

        if (duration != null) animatorSet.setDuration(duration);
        if (interpolator != null) animatorSet.setInterpolator(interpolator);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (startListener != null) startListener.onStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (stopListener != null) stopListener.onStop();
                if (nextAnimator != null) {
                    nextAnimator.prevAnimator = null;
                    nextAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });

        return animatorSet;
    }

    public EEViewAnimator start() {
        if (prevAnimator != null) {
            prevAnimator.start();
        } else {
            animatorSet = createAnimatorSet();

            if (waitView != null) {
                waitView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        animatorSet.start();
                        waitView.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
            } else {
                animatorSet.start();
            }
        }
        return this;
    }

    public void cancel() {
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (nextAnimator != null) {
            nextAnimator.cancel();
            nextAnimator = null;
        }
    }

    public EEViewAnimator duration(long duration) {
        this.duration = duration;
        return this;
    }

    public EEViewAnimator onStart(EEAnimationListener.Start startListener) {
        this.startListener = startListener;
        return this;
    }

    public EEViewAnimator onStop(EEAnimationListener.Stop stopListener) {
        this.stopListener = stopListener;
        return this;
    }

    public EEViewAnimator interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }
}
