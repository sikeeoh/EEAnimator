package com.sikeeo.eeanimator;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sikeeo on 2016. 1. 4..
 */
public class EEAnimationBuilder {

    private final EEViewAnimator eeViewAnimator;
    private final View[] views;
    private final ArrayList<Animator> animatorList;

    public boolean waitForView;
    public boolean isDp;

    public EEAnimationBuilder(EEViewAnimator eeViewAnimator, View... views) {
        this.eeViewAnimator = eeViewAnimator;
        this.views = views;
        isDp = false;
        animatorList = new ArrayList<>();
    }

    public EEAnimationBuilder dpValue() {
        isDp = true;
        return this;
    }

    public EEAnimationBuilder add(Animator animator) {
        this.animatorList.add(animator);
        return this;
    }

    public float pxToDp(final float px) {
        return px / views[0].getContext().getResources().getDisplayMetrics().density;
    }

    public float dpToPx(final float dp) {
        return dp * views[0].getContext().getResources().getDisplayMetrics().density;
    }

    public float[] getValues(float... values) {
        if(!isDp) return values;

        int length = values.length;
        float[] pxValues = new float[length];
        for (int i =0; i < length; ++i) {
            pxValues[i] = dpToPx(values[i]);
        }
        return pxValues;
    }

    public EEAnimationBuilder property(String propertyName, float... values) {
        for(View view : views) {
            this.animatorList.add(ObjectAnimator.ofFloat(view, propertyName, getValues(values)));
        }
        return this;
    }

    public EEAnimationBuilder translationX(float... x) {
        return property(EEAnimationProperty.TRANSLATION_X, x);
    }

    public EEAnimationBuilder translationY(float... y) {
        return property(EEAnimationProperty.TRANSLATION_Y, y);
    }

    public EEAnimationBuilder alpha(float... alpha) {
        return property(EEAnimationProperty.ALPHA, alpha);
    }

    public EEAnimationBuilder scaleX(float... scaleX) {
        return property(EEAnimationProperty.SCALE_X, scaleX);
    }

    public EEAnimationBuilder scaleY(float... scaleY) {
        return property(EEAnimationProperty.SCALE_Y, scaleY);
    }

    public EEAnimationBuilder scale(float... scale) {
        scaleX(scale);
        scaleY(scale);
        return this;
    }

    public EEAnimationBuilder pivotX(float pivotX) {
        for(View view : views) {
            view.setPivotX(pivotX);
        }
        return this;
    }

    public EEAnimationBuilder pivotY(float pivotY) {
        for(View view : views) {
            view.setPivotY(pivotY);
        }
        return this;
    }

    public EEAnimationBuilder rotationX(float... rotationX) {
        return property(EEAnimationProperty.ROTATION_X, rotationX);
    }

    public EEAnimationBuilder rotationY(float... rotationY) {
        return property(EEAnimationProperty.ROTATION_Y, rotationY);
    }

    public EEAnimationBuilder rotation(float... rotation) {
        return property(EEAnimationProperty.ROTATION, rotation);
    }

    public EEAnimationBuilder backgroundColor(int... colors) {
        for(View view : views) {
            if(view instanceof TextView) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, EEAnimationProperty.BACKGROUND_COLOR, colors);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                this.animatorList.add(objectAnimator);
            }
        }

        return this;
    }

    public EEAnimationBuilder textColor(int... colors) {
        for(View view : views) {
            if (view instanceof TextView) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, EEAnimationProperty.TEXT_COLOR, colors);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                this.animatorList.add(objectAnimator);
            }
        }
        return this;
    }

    public EEAnimationBuilder custom(final EEAnimationListener.Update updateListener, float... values) {
        for(final View view : views) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(getValues(values));
            if (updateListener != null) {
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        updateListener.update(view, Float.parseFloat(animation.getAnimatedValue().toString()));
                    }
                });
                add(valueAnimator);
            }
        }
        return this;
    }

    public EEAnimationBuilder height(float... height) {
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().height = Math.round(value);
                view.requestLayout();
            }
        }, height);
    }

    public EEAnimationBuilder width(float... width) {
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().width = Math.round(value);
                view.requestLayout();
            }
        }, width);
    }

    public EEAnimationBuilder waitForView() {
        waitForView = true;
        return this;
    }

    public List<Animator> createAnimators() {
        return animatorList;
    }

    public EEAnimationBuilder nextAnimate(View... views) {
        return eeViewAnimator.nextAnimate(views);
    }

    public EEViewAnimator start() {
        return eeViewAnimator.start();
    }

    public EEViewAnimator duration(long duration) {
        return eeViewAnimator.duration(duration);
    }

    public EEViewAnimator startDelay(long startDelay) {
        return eeViewAnimator.startDelay(startDelay);
    }

    public EEViewAnimator onStart(EEAnimationListener.Start startListener) {
        return eeViewAnimator.onStart(startListener);
    }

    public EEViewAnimator onStop(EEAnimationListener.Stop stopListener) {
        return eeViewAnimator.onStop(stopListener);
    }

    public EEViewAnimator interpolator(Interpolator interpolator) {
        return eeViewAnimator.interpolator(interpolator);
    }

    public EEViewAnimator accelerate() {
        return eeViewAnimator.interpolator(new AccelerateInterpolator());
    }

    public EEViewAnimator descelerate() {
        return eeViewAnimator.interpolator(new DecelerateInterpolator());
    }

    public EEViewAnimator accelerateDecelerate() {
        return eeViewAnimator.interpolator(new AccelerateDecelerateInterpolator());
    }

    public EEViewAnimator anticipate() {
        return eeViewAnimator.interpolator(new AnticipateInterpolator());
    }

    public EEViewAnimator overShoot() {
        return eeViewAnimator.interpolator(new OvershootInterpolator());
    }

    public EEViewAnimator anticipateOvershoot() {
        return eeViewAnimator.interpolator(new AnticipateOvershootInterpolator());
    }

    public EEViewAnimator bounce() {
        return eeViewAnimator.interpolator(new BounceInterpolator());
    }

    public View[] getViews() {
        return views;
    }

    public View getView() {
        return views[0];
    }

    public boolean isWaitForView() {
        return waitForView;
    }
}
