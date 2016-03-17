package com.webarch.srmobile.activities.sub.evarsity;

import android.os.Bundle;

import com.webarch.srmobile.activities.sub.SubActivity;
import com.webarch.srmobile.activities.sub.evarsity.views.AttendanceFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.ChangePasswordFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.ExamTimetableFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.GPAsFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.MarksFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.NotificationsFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.ProfileFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.ResultsFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.TimetableFragment;
import com.webarch.srmobile.activities.sub.evarsity.views.subjects.SubjectsFragment;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.views.signin.SignInDialogFragment;

/**
 * @author Manoj Khanna
 */

public class EvarsityActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null && !User.isSignedIn()) {
            new SignInDialogFragment().show(getSupportFragmentManager(), SignInDialogFragment.class.getName());
        }
    }

    @Override
    protected String getSubActivityTitle() {
        return "E-varsity";
    }

    @Override
    public String[] getFragmentTitles() {
        return new String[]{
                "Notifications",
                "Profile",
                "Subjects",
                "Attendance",
                "Marks",
                "GPAs",
                "Timetable",
                "Exam timetable",
                "Change password",
                "Results"
        };
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[]{
                NotificationsFragment.class,
                ProfileFragment.class,
                SubjectsFragment.class,
                AttendanceFragment.class,
                MarksFragment.class,
                GPAsFragment.class,
                TimetableFragment.class,
                ExamTimetableFragment.class,
                ChangePasswordFragment.class,
                ResultsFragment.class
        };
    }

}
