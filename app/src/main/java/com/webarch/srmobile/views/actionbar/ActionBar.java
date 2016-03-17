package com.webarch.srmobile.views.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.webarch.srmobile.R;

/**
 * @author Manoj Khanna
 */

public class ActionBar extends RelativeLayout {

    private OnClickListener onClickListener;

    public ActionBar(final Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.action_bar, this);
        setBackgroundResource(R.color.dark);

        findViewById(R.id.action_bar_quick_access).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickListener.onQuickAccessClicked();
            }

        });

        findViewById(R.id.action_bar_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickListener.onBackClicked();
            }

        });

        findViewById(R.id.action_bar_search).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickListener.onSearchClicked();
            }

        });
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {

        public void onQuickAccessClicked();

        public void onBackClicked();

        public void onSearchClicked();

    }

}
