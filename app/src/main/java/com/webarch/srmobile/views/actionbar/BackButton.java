package com.webarch.srmobile.views.actionbar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.utils.animation.ColorAnimation;
import com.webarch.srmobile.views.colorbuttons.BaseColorButton;
import com.webarch.srmobile.views.colorbuttons.ColorButton;

/**
 * @author Manoj Khanna
 */

public class BackButton extends LinearLayout implements ColorButton, ColorAnimation.OnColorChangeListener {

    private BaseColorButton baseColorButton;

    public BackButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        baseColorButton = new BaseColorButton(this, Color.TRANSPARENT, getResources().getColor(R.color.normal));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return baseColorButton.onTouchEvent(event);
    }

    @Override
    public void onColorChanged(int color) {
        setBackgroundColor(color);
    }

}
