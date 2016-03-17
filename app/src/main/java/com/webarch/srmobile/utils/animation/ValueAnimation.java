package com.webarch.srmobile.utils.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author Manoj Khanna
 */

public class ValueAnimation extends Animation {

    private float fromValue, toValue;
    private OnValueChangeListener onValueChangeListener;

    public ValueAnimation(float fromValue, float toValue, OnValueChangeListener onValueChangeListener) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.onValueChangeListener = onValueChangeListener;
    }

    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    @Override
    public boolean willChangeBounds() {
        return false;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        onValueChangeListener.onValueChanged(fromValue + (toValue - fromValue) * interpolatedTime);
    }

    public interface OnValueChangeListener {

        public void onValueChanged(float value);

    }

}
