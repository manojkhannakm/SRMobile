package com.webarch.srmobile.activities.main.views.subactivitybutton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

import com.webarch.srmobile.views.colorbuttons.ImageButton;

/**
 * @author Manoj Khanna
 */

public class IconButton extends ImageButton {

    private GradientDrawable backgroundGradientDrawable;

    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        backgroundGradientDrawable = (GradientDrawable) ((LayerDrawable) background).getDrawable(0).mutate();

        super.setBackgroundDrawable(background);
    }

    @Override
    public void onColorChanged(int color) {
        backgroundGradientDrawable.setColor(color);
    }

}
