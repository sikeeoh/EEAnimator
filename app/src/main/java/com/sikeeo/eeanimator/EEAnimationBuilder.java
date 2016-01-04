package com.sikeeo.eeanimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deiphoks on 2016. 1. 4..
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
}
