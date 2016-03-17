package com.webarch.srmobile.views.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.webarch.srmobile.R;

/**
 * @author Manoj Khanna
 */

public class SearchPane extends LinearLayout {

    public SearchPane(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.search_pane, this);
        setBackgroundResource(R.color.normal);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
