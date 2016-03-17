package com.webarch.srmobile.activities.sub.about;

import com.webarch.srmobile.activities.sub.SubActivity;
import com.webarch.srmobile.activities.sub.about.views.AcademicsFragment;
import com.webarch.srmobile.activities.sub.about.views.AdmissionFragment;
import com.webarch.srmobile.activities.sub.about.views.CampusLifeFragment;
import com.webarch.srmobile.activities.sub.about.views.ResearchFragment;
import com.webarch.srmobile.activities.sub.about.views.SRMFragment;

/**
 * @author Manoj Khanna
 */

public class AboutActivity extends SubActivity {

    @Override
    protected String getSubActivityTitle() {
        return "About";
    }

    @Override
    public String[] getFragmentTitles() {
        return new String[]{
                "SRM",
                "Admission",
                "Academics",
                "Research",
                "Campus life"
        };
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[]{
                SRMFragment.class,
                AdmissionFragment.class,
                AcademicsFragment.class,
                ResearchFragment.class,
                CampusLifeFragment.class
        };
    }

}
