package com.sikeeo.eeanimator;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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

    public EEAnimationBuilder add(Animator animator) {
        this.animatorList.add(animator);
        return this;
    }

    public EEAnimationBuilder property(String propertyName, float... values) {
        for (View view : views) {
            this.animatorList.add(ObjectAnimator.ofFloat(view, propertyName, values));
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


    public EEAnimationBuilder fadeIn() {
        return alpha(0, 0.25f, 0.5f, 0.75f, 1);
    }

    public EEAnimationBuilder fadeOut() {
        return alpha(1, 0.75f, 0.5f, 0.25f, 0);
    }

    public EEAnimationBuilder flash() {
        return alpha(1, 0, 1, 0, 1);
    }

    public EEAnimationBuilder flipHorizontal() {
        return rotationX(90, -15, 15, 0);
    }

    public EEAnimationBuilder flipVertical() {
        return rotationY(90, -15, 15, 0);
    }

    public EEAnimationBuilder pulse() {
        return scaleY(1, 1.1f, 1).scaleX(1, 1.1f, 1);
    }

    public EEAnimationBuilder rollIn() {
        View target = getView();
        return alpha(0, 1)
                .translationX(-(target.getWidth() - target.getPaddingLeft() - target.getPaddingRight()), 0)
                .rotation(-120, 0);
    }

    public EEAnimationBuilder rollOut() {
        View target = getView();
        return alpha(1, 0)
                .translationX(0, target.getWidth())
                .rotation(0, 120);
    }

    public EEAnimationBuilder rubber() {
        return scaleX(1, 1.25f, 0.75f, 1.15f, 1).scaleY(1, 0.75f, 1.25f, 0.85f, 1);
    }

    public EEAnimationBuilder shake() {
        return translationX(0, 25, -25, 25, -25, 15, -15, 6, -6, 0);
    }

    public EEAnimationBuilder swing() {
        return rotation(0, 10, -10, 6, -6, 3, -3, 0);
    }

    public EEAnimationBuilder tada() {
        return scaleX(1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1)
                .scaleY(1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1)
                .rotation(0, -3, -3, 3, -3, 3, -3, 3, -3, 0);
    }

    public EEAnimationBuilder wobble() {
        View target = getView();
        float width = target.getWidth();
        float one = (float) (width / 100.0);
        return translationX(0 * one, -25 * one, 20 * one, -15 * one, 10 * one, -5 * one, 0 * one, 0)
                .rotation(0, -5, 3, -3, 2, -1, 0);
    }

    public EEAnimationBuilder zoomIn() {
        return scaleX(0.45f, 1).scaleY(0.45f, 1).alpha(0, 1);
    }

    public EEAnimationBuilder zoomOut() {
        return scaleX(1, 0.3f, 0).scaleY(1, 0.3f, 0).alpha(1, 0, 0);
    }


    public EEAnimationBuilder scale(float... scale) {
        scaleX(scale);
        scaleY(scale);
        return this;
    }

    public EEAnimationBuilder pivotX(float pivotX) {
        for (View view : views) {
            view.setPivotX(pivotX);
        }
        return this;
    }

    public EEAnimationBuilder pivotY(float pivotY) {
        for (View view : views) {
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
        for (View view : views) {
            if (view instanceof TextView) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, EEAnimationProperty.BACKGROUND_COLOR, colors);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                this.animatorList.add(objectAnimator);
            }
        }

        return this;
    }

    public EEAnimationBuilder backgroundColorRes(int... colorResIds) {
        return backgroundColor(getColorsByResIds(colorResIds));
    }

    public EEAnimationBuilder textColor(int... colors) {
        for (View view : views) {
            if (view instanceof TextView) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, EEAnimationProperty.TEXT_COLOR, colors);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                this.animatorList.add(objectAnimator);
            }
        }
        return this;
    }

    public EEAnimationBuilder textColorRes(int... colorResIds) {
        return textColor(getColorsByResIds(colorResIds));
    }

    private EEAnimationBuilder custom(final EEAnimationListener.Update updateListener, float... values) {
        for (final View view : views) {
            if (values == null) {
                values = new float[1];
            }

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(values);
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

    public EEAnimationBuilder heightDp(float... height) {
        if (!waitForView) waitForView();
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().height = toPx(Math.round(value));
                view.requestLayout();
            }
        }, height);
    }

    public EEAnimationBuilder heightPx(float... height) {
        if (!waitForView) waitForView();
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().height = Math.round(value);
                view.requestLayout();
            }
        }, height);
    }

    public EEAnimationBuilder heightRes(int... dimenResIds) {
        return heightDp(getSizesByResIds(dimenResIds));
    }

    public EEAnimationBuilder widthDp(float... width) {
        if (!waitForView) waitForView();
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().width = toPx(Math.round(value));
                view.requestLayout();
            }
        }, width);
    }

    public EEAnimationBuilder widthPx(float... width) {
        if (!waitForView) waitForView();
        return custom(new EEAnimationListener.Update() {
            @Override
            public void update(View view, float value) {
                view.getLayoutParams().width = Math.round(value);
                view.requestLayout();
            }
        }, width);
    }

    public EEAnimationBuilder widthRes(int... dimenResIds) {
        return widthDp(getSizesByResIds(dimenResIds));
    }

    private int toPx(final float dp) {
        return (int) (dp * views[0].getContext().getResources().getDisplayMetrics().density);
    }

    private EEAnimationBuilder waitForView() {
        waitForView = true;
        return this;
    }

    protected List<Animator> createAnimators() {
        return animatorList;
    }

    public EEAnimationBuilder nextAnimate(View... views) {
        return eeViewAnimator.nextAnimate(views);
    }

    public EEAnimationBuilder andAnimate(View... views) {
        return eeViewAnimator.addAnimationBuilder(views);
    }

    public EEViewAnimator start() {
        return eeViewAnimator.start();
    }

    public EEViewAnimator duration(long duration) {
        return eeViewAnimator.duration(duration);
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

    protected boolean isWaitForView() {
        return waitForView;
    }

    private int[] getColorsByResIds(int... resIds) {
        Context context = views[0].getContext();
        int[] colors = new int[resIds.length];

        for (int i = 0; i < resIds.length; i++) {
            colors[i] = context.getResources().getColor(resIds[i]);
        }

        return colors;
    }

    private float[] getSizesByResIds(int... resIds) {
        Context context = views[0].getContext();
        float[] sizes = new float[resIds.length];

        for (int i = 0; i < resIds.length; i++) {
            sizes[i] = context.getResources().getDimension(resIds[i]);
        }

        return sizes;
    }
}
