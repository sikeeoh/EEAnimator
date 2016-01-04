package com.sikeeo.eeanimator;

import android.view.View;

/**
 * Created by sikeeo on 2016. 1. 4..
 */
public class EEAnimationListener {
    private EEAnimationListener(){}

    public interface Start{
        void onStart();
    }

    public interface Stop{
        void onStop();
    }

    public interface Update<V extends View>{
        void update(V view, float value);
    }
}
