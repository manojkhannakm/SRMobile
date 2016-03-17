package com.webarch.srmobile.activities.main.views.subactivitybutton;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.main.MainActivity;
import com.webarch.srmobile.utils.animation.AnimationListenerAdapter;
import com.webarch.srmobile.utils.animation.AnimationObject;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.views.Text;

/**
 * @author Manoj Khanna
 */

public class SubActivityButton extends RelativeLayout {

    private RelativeLayout notificationLayout;
    private AnimationObject notificationAnimationObject;

    public SubActivityButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.main_sub_activity_button, this);
        setVisibility(INVISIBLE);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubActivityButton);

        IconButton iconButton = (IconButton) findViewById(R.id.main_sub_activity_button_icon);
        iconButton.setImageResource(typedArray.getResourceId(R.styleable.SubActivityButton_icon, -1));
        iconButton.setOnClickListener(new OnClickListener() {

            private Class activityClass;

            {
                try {
                    activityClass = Class.forName(typedArray.getString(R.styleable.SubActivityButton_activity));
                } catch (ClassNotFoundException ex) {
                    Log.e(SubActivityButton.class.getName(), ex.toString());
                }
            }

            @Override
            public void onClick(View v) {
                ((MainActivity) context).startActivityForResult(new Intent(context, activityClass), 0);
            }

        });

        ((Text) findViewById(R.id.main_sub_activity_button_text)).setText(typedArray.getString(R.styleable.SubActivityButton_text));

        typedArray.recycle();
    }

    public void show(long startOffset) {
        AnimationUtils.zoomIn(this, true, startOffset, new OvershootInterpolator(), null);
    }

    private void showNotification(final int notificationCount) {
        if (notificationLayout != null) {
            final Text notificationCountText = (Text) notificationLayout.findViewById(R.id.main_sub_activity_button_notification_count);
            AnimationUtils.scale(notificationCountText, 1.25f, 1, 1.25f, 1, 0.5f, 0.5f, 0, 1, 0, 250, null, new AnimationListenerAdapter() {

                @Override
                public void onAnimationStart() {
                    notificationCountText.setText(String.valueOf(notificationCount));
                }

            });
            return;
        }

        notificationLayout = (RelativeLayout) inflate(getContext(), R.layout.main_sub_activity_button_notification, this);
        LayoutParams notificationLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        notificationLayoutParams.addRule(ALIGN_RIGHT, R.id.main_sub_activity_button_icon);
        notificationLayoutParams.addRule(ALIGN_TOP, R.id.main_sub_activity_button_icon);
        notificationLayout.setLayoutParams(notificationLayoutParams);
        notificationLayout.setVisibility(INVISIBLE);

        ((Text) notificationLayout.findViewById(R.id.main_sub_activity_button_notification_count)).setText(String.valueOf(notificationCount));

        if (notificationAnimationObject != null) {
            notificationAnimationObject.end();
        }
        notificationAnimationObject = AnimationUtils.zoomIn(notificationLayout, true, 0, new OvershootInterpolator(), null);
    }

    private void hideNotification() {
        if (notificationAnimationObject != null) {
            notificationAnimationObject.end();
        }
        notificationAnimationObject = AnimationUtils.zoomOut(notificationLayout, true, 0, new OvershootInterpolator(), new AnimationListenerAdapter() {

            @Override
            public void onAnimationEnd() {
                removeView(notificationLayout);
            }

        });

        notificationLayout = null;
    }

}
