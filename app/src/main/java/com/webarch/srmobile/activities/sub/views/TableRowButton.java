package com.webarch.srmobile.activities.sub.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TableRow;

import com.webarch.srmobile.R;
import com.webarch.srmobile.utils.animation.ColorAnimation;
import com.webarch.srmobile.views.colorbuttons.BaseColorButton;
import com.webarch.srmobile.views.colorbuttons.ColorButton;

/**
 * @author Manoj Khanna
 */

public class TableRowButton extends TableRow implements ColorButton, ColorAnimation.OnColorChangeListener {

    private BaseColorButton baseColorButton;

    public TableRowButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        baseColorButton = new BaseColorButton(this, Color.TRANSPARENT, getResources().getColor(R.color.light));
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
