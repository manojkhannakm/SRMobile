package com.webarch.srmobile.views.signin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.webarch.srmobile.activities.sub.evarsity.EvarsityActivity;
import com.webarch.srmobile.tasks.SignInAsyncTask;

/**
 * @author Manoj Khanna
 */

public class SigningInDialogFragment extends DialogFragment {

    private SignInAsyncTask signInAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (signInAsyncTask == null) {
            dismiss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Signing in...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCancel(dialog);
            }

        });
        return progressDialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        signInAsyncTask.cancel(true);

        FragmentActivity activity = getActivity();
        if (activity instanceof EvarsityActivity) {
            activity.finish();
        }
    }

    public void setSignInAsyncTask(SignInAsyncTask signInAsyncTask) {
        this.signInAsyncTask = signInAsyncTask;
    }

}
