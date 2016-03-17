package com.webarch.srmobile.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Manoj Khanna
 */

public class Text extends TextView {

    private static Typeface bakerSignetTypeface;

    public Text(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (bakerSignetTypeface == null) {
            bakerSignetTypeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/baker_signet.ttf");
        }

        setTypeface(bakerSignetTypeface);
    }

}
