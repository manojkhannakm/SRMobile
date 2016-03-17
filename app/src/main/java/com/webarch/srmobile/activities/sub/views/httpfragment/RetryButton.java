package com.webarch.srmobile.activities.sub.views.httpfragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.webarch.srmobile.views.colorbuttons.ImageButton;

/**
 * @author Manoj Khanna
 */

public class RetryButton extends ImageButton {

    private GradientDrawable backgroundGradientDrawable;

    public RetryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        backgroundGradientDrawable = (GradientDrawable) background.mutate();

        super.setBackgroundDrawable(background);
    }

    @Override
    public void onColorChanged(int color) {
        backgroundGradientDrawable.setColor(color);
    }

}
