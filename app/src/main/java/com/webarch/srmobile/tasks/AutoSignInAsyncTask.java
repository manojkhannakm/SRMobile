package com.webarch.srmobile.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.Activity;
import com.webarch.srmobile.activities.main.MainActivity;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.parsers.SignInParser;
import com.webarch.srmobile.sqlitehelpers.UsersSqliteHelper;
import com.webarch.srmobile.views.quickaccess.UserButton;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class AutoSignInAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private UserButton userButton;
    private String cookie;

    public AutoSignInAsyncTask(Activity activity) {
        this.activity = activity;
        userButton = (UserButton) activity.findViewById(R.id.quick_access_pane_user_button);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        UsersSqliteHelper usersSqliteHelper = new UsersSqliteHelper(activity);
        String[] credentials = usersSqliteHelper.readCredentials();
        usersSqliteHelper.close();
        if (credentials == null) {
            return false;
        }

        if (isCancelled()) {
            return null;
        }

        try {
            SignInParser signInParser = new SignInParser(credentials[0], credentials[1]);
            try {
                signInParser.execute();
            } catch (ParserException ignored) {
            }

            if (isCancelled()) {
                return null;
            }

            if (signInParser.isError()) {
                User.destroy(activity);
                return false;
            }

            cookie = signInParser.getCookie();
        } catch (IOException ex) {
            Log.e(AutoSignInAsyncTask.class.getName(), ex.toString());
        }

        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        userButton.signingIn();
    }

    @Override
    protected void onPostExecute(Boolean signedIn) {
        super.onPostExecute(signedIn);

        if (signedIn) {
            User.create(activity, cookie);

            userButton.signIn();

            if (activity instanceof MainActivity) {
                ((MainActivity) activity).onSignedIn();
            }
        } else {
            userButton.signOut();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        userButton.signOut();
    }

}
