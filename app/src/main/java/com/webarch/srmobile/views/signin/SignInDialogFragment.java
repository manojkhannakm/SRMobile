package com.webarch.srmobile.views.signin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.Activity;
import com.webarch.srmobile.activities.sub.evarsity.EvarsityActivity;
import com.webarch.srmobile.tasks.SignInAsyncTask;

/**
 * @author Manoj Khanna
 */

public class SignInDialogFragment extends DialogFragment {

    private EditText regNoEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().getSharedPreferences(SignInDialogFragment.class.getName(), Context.MODE_PRIVATE).edit()
                .putString("regNo", regNoEditText.getText().toString())
                .putString("password", passwordEditText.getText().toString())
                .commit();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final FragmentActivity activity = getActivity();
        final View view = View.inflate(activity, R.layout.sign_in_dialog, null);
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SignInDialogFragment.class.getName(), Context.MODE_PRIVATE);
        String regNo = sharedPreferences.getString("regNo", "");

        regNoEditText = (EditText) view.findViewById(R.id.sign_in_dialog_reg_no);
        regNoEditText.setText(regNo);

        passwordEditText = (EditText) view.findViewById(R.id.sign_in_dialog_password);
        passwordEditText.setText(sharedPreferences.getString("password", ""));

        if (regNo.length() < 10) {
            regNoEditText.requestFocus();
        } else {
            passwordEditText.requestFocus();
        }

        return new AlertDialog.Builder(activity)
                .setTitle("Sign in")
                .setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCancel(dialog);
                    }

                })
                .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

                        new SignInAsyncTask((Activity) activity).execute(regNoEditText.getText().toString(), passwordEditText.getText().toString());
                    }

                }).create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        FragmentActivity activity = getActivity();
        if (activity instanceof EvarsityActivity) {
            activity.finish();
        }
    }

}
