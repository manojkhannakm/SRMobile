package com.webarch.srmobile.views.colorbuttons;

import android.view.MotionEvent;
import android.view.View;

import com.webarch.srmobile.utils.animation.AnimationListenerAdapter;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.utils.animation.ColorAnimation;

/**
 * @author Manoj Khanna
 */

public class BaseColorButton implements ColorButton {

    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_ANIMATING = 2;
    private View view;
    private int normalColor, pressedColor;
    private int state;

    public BaseColorButton(View view, int normalColor, int pressedColor) {
        this.view = view;
        this.normalColor = normalColor;
        this.pressedColor = pressedColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!view.isClickable()) {
            return false;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (state != STATE_RELEASED) {
                    break;
                }
                state = STATE_PRESSED;

                ((ColorAnimation.OnColorChangeListener) view).onColorChanged(pressedColor);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX(),
                        y = event.getY();
                if (x < 0 || y < 0 || x > view.getWidth() || y > view.getHeight()) {
                    state = STATE_RELEASED;

                    ((ColorAnimation.OnColorChangeListener) view).onColorChanged(normalColor);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                state = STATE_RELEASED;

                ((ColorAnimation.OnColorChangeListener) view).onColorChanged(normalColor);
                break;

            case MotionEvent.ACTION_UP:
                if (state != STATE_PRESSED) {
                    break;
                }
                state = STATE_ANIMATING;

                AnimationUtils.changeColor(view, pressedColor, normalColor, 0, null, (ColorAnimation.OnColorChangeListener) view, new AnimationListenerAdapter() {

                    @Override
                    public void onAnimationEnd() {
                        state = STATE_RELEASED;
                    }

                });

                view.performClick();
                break;

        }
        return true;
    }

}
