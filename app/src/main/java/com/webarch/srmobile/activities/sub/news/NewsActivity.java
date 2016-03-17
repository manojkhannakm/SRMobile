package com.webarch.srmobile.activities.sub.news;

import com.webarch.srmobile.activities.sub.SubActivity;

/**
 * @author Manoj Khanna
 */

public class NewsActivity extends SubActivity {

    @Override
    protected String getSubActivityTitle() {
        return "News";
    }

    @Override
    public String[] getFragmentTitles() {
        return new String[0];
    }

    @Override
    public Class[] getFragmentClasses() {
        return new Class[0];
    }

}
