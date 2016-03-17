package com.webarch.srmobile.activities.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.Activity;
import com.webarch.srmobile.activities.main.views.subactivitybutton.SubActivityButton;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.tasks.AutoSignInAsyncTask;
import com.webarch.srmobile.utils.AndroidUtils;
import com.webarch.srmobile.utils.animation.AnimationListenerAdapter;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.views.Text;
import com.webarch.srmobile.views.actionbar.ActionBar;
import com.webarch.srmobile.views.quickaccess.QuickAccessPane;
import com.webarch.srmobile.views.quickaccess.UserButton;
import com.webarch.srmobile.views.search.SearchPane;

/**
 * @author Manoj Khanna
 */

public class MainActivity extends Activity {

    private boolean splashGone;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!User.isSignedIn()) {
            userButton.signOut();

            autoSignInAsyncTask = null;
        } else {
            userButton.signIn();

            onSignedIn();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.webarch.srmobile.R.layout.main);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        quickAccessPane = (QuickAccessPane) findViewById(R.id.main_quick_access_pane);
        userButton = (UserButton) findViewById(R.id.quick_access_pane_user_button);
        searchPane = (SearchPane) findViewById(R.id.main_search_pane);
        if (savedInstanceState != null) {
            splashGone = savedInstanceState.getBoolean("splashGone");
        }

        final Bitmap patternBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pattern_background);

        final ImageView headerImageView = (ImageView) findViewById(R.id.main_header);
        AndroidUtils.postLayout(headerImageView, new Runnable() {

            @Override
            public void run() {
                int patternBackgroundBitmapWidth = patternBackgroundBitmap.getWidth();
                //noinspection deprecation
                headerImageView.setBackgroundDrawable(new BitmapDrawable(getResources(),
                        Bitmap.createBitmap(patternBackgroundBitmap, 0, 0,
                                patternBackgroundBitmapWidth, headerImageView.getHeight() * patternBackgroundBitmapWidth / headerImageView.getWidth())
                ));
            }

        });

        ActionBar actionBar = (ActionBar) findViewById(R.id.main_action_bar);
        ((Text) actionBar.findViewById(R.id.action_bar_title)).setText("Home");
        actionBar.setOnClickListener(this);

        userButton.setOnClickListener(this);

        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.main_splash);
        //noinspection deprecation
        splashLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), patternBackgroundBitmap));
        splashLayout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }

        });

        if (!splashGone) {
            AnimationUtils.fadeOut(splashLayout, 1500, new AnimationListenerAdapter() {

                @Override
                public void onAnimationStart() {
                    splashGone = true;
                }

                @Override
                public void onAnimationEnd() {
                    splashLayout.setVisibility(View.GONE);

                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_about)).show(0);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_feedback)).show(0);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_faculties)).show(100);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_links)).show(100);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_news)).show(200);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_credits)).show(200);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_evarsity)).show(300);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_planner)).show(300);
                    ((SubActivityButton) findViewById(R.id.main_sub_activity_button_ecampus)).show(400);
                }

            });
        } else {
            splashLayout.setVisibility(View.GONE);

            RelativeLayout subActivityButtonsLayout = (RelativeLayout) findViewById(R.id.main_sub_activity_buttons);
            for (int i = 0, n = subActivityButtonsLayout.getChildCount(); i < n; i++) {
                subActivityButtonsLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }

        if (!User.isSignedIn()) {
            autoSignInAsyncTask = new AutoSignInAsyncTask(this);
            autoSignInAsyncTask.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("splashGone", splashGone);
    }

    public void onSignedIn() {
        autoSignInAsyncTask = null;
        signInAsyncTask = null;

        //TODO: Run task queue here
    }

}
