package com.webarch.srmobile.views.signout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.evarsity.EvarsityActivity;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.views.quickaccess.UserButton;

/**
 * @author Manoj Khanna
 */

public class SignOutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FragmentActivity activity = getActivity();
        return new AlertDialog.Builder(activity)
                .setTitle("Sign out")
                .setMessage("Are you sure?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Sign out", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.destroy(activity);

                        if (activity instanceof EvarsityActivity) {
                            activity.finish();
                        } else {
                            ((UserButton) activity.findViewById(R.id.quick_access_pane_user_button)).signOut();
                        }
                    }

                }).create();
    }

}
