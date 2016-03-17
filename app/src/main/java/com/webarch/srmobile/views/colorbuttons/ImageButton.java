package com.webarch.srmobile.views.colorbuttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.webarch.srmobile.R;
import com.webarch.srmobile.utils.animation.ColorAnimation;

/**
 * @author Manoj Khanna
 */

public class ImageButton extends ImageView implements ColorButton, ColorAnimation.OnColorChangeListener {

    private BaseColorButton baseColorButton;

    public ImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageButton);
        baseColorButton = new BaseColorButton(this,
                typedArray.getColor(R.styleable.ImageButton_normal_color, -1),
                typedArray.getColor(R.styleable.ImageButton_pressed_color, -1));
        typedArray.recycle();
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
