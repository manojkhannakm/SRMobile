package com.webarch.srmobile.utils.animation;

import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author Manoj Khanna
 */

public class ColorAnimation extends Animation {

    private int fromColor, toColor;
    private OnColorChangeListener onColorChangeListener;

    public ColorAnimation(int fromColor, int toColor, OnColorChangeListener onColorChangeListener) {
        this.fromColor = fromColor;
        this.toColor = toColor;
        this.onColorChangeListener = onColorChangeListener;
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
        int fromAlpha = Color.alpha(fromColor),
                fromRed = Color.red(fromColor),
                fromGreen = Color.green(fromColor),
                fromBlue = Color.blue(fromColor);

        onColorChangeListener.onColorChanged(Color.argb((int) (fromAlpha + (Color.alpha(toColor) - fromAlpha) * interpolatedTime),
                (int) (fromRed + (Color.red(toColor) - fromRed) * interpolatedTime),
                (int) (fromGreen + (Color.green(toColor) - fromGreen) * interpolatedTime),
                (int) (fromBlue + (Color.blue(toColor) - fromBlue) * interpolatedTime)));
    }

    public interface OnColorChangeListener {

        public void onColorChanged(int color);

    }

}
