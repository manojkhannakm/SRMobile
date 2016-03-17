package com.webarch.srmobile.activities;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.tasks.AutoSignInAsyncTask;
import com.webarch.srmobile.tasks.SignInAsyncTask;
import com.webarch.srmobile.views.actionbar.ActionBar;
import com.webarch.srmobile.views.quickaccess.QuickAccessPane;
import com.webarch.srmobile.views.quickaccess.UserButton;
import com.webarch.srmobile.views.search.SearchPane;
import com.webarch.srmobile.views.signin.SignInDialogFragment;
import com.webarch.srmobile.views.signout.SignOutDialogFragment;

/**
 * @author Manoj Khanna
 */

public class Activity extends FragmentActivity implements ActionBar.OnClickListener, UserButton.OnClickListener {

    protected DrawerLayout drawerLayout;
    protected QuickAccessPane quickAccessPane;
    protected UserButton userButton;
    protected SearchPane searchPane;
    protected AutoSignInAsyncTask autoSignInAsyncTask;
    protected SignInAsyncTask signInAsyncTask;

    @Override
    protected void onPause() {
        if (autoSignInAsyncTask != null && autoSignInAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            autoSignInAsyncTask.cancel(true);
        }

        if (signInAsyncTask != null && signInAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            signInAsyncTask.cancel(true);
            signInAsyncTask = null;
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (autoSignInAsyncTask != null && autoSignInAsyncTask.isCancelled()) {
            autoSignInAsyncTask = new AutoSignInAsyncTask(this);
            autoSignInAsyncTask.execute();
        } else if (User.isSignedIn()) {
            userButton.signIn();
        }
    }

    @Override
    public void onQuickAccessClicked() {
        if (drawerLayout.isDrawerOpen(searchPane)) {
            drawerLayout.closeDrawer(searchPane);
        }

        if (!drawerLayout.isDrawerOpen(quickAccessPane)) {
            drawerLayout.openDrawer(quickAccessPane);
        } else {
            drawerLayout.closeDrawer(quickAccessPane);
        }
    }

    @Override
    public void onBackClicked() {
        finish();
    }

    @Override
    public void onSearchClicked() {
        if (drawerLayout.isDrawerOpen(quickAccessPane)) {
            drawerLayout.closeDrawer(quickAccessPane);
        }

        if (!drawerLayout.isDrawerOpen(searchPane)) {
            drawerLayout.openDrawer(searchPane);
        } else {
            drawerLayout.closeDrawer(searchPane);
        }
    }

    @Override
    public void onUserClicked() {
        autoSignInAsyncTask = null;

        if (!User.isSignedIn()) {
            new SignInDialogFragment().show(getSupportFragmentManager(), SignInDialogFragment.class.getName());
        } else {
            new SignOutDialogFragment().show(getSupportFragmentManager(), SignOutDialogFragment.class.getName());
        }
    }

    public void setSignInAsyncTask(SignInAsyncTask signInAsyncTask) {
        this.signInAsyncTask = signInAsyncTask;
    }

}
