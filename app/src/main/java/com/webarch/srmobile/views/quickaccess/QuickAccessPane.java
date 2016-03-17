package com.webarch.srmobile.views.quickaccess;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.webarch.srmobile.R;

/**
 * @author Manoj Khanna
 */

public class QuickAccessPane extends LinearLayout {

    public QuickAccessPane(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.quick_access_pane, this);
        setBackgroundResource(R.color.normal);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
