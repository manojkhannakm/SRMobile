package com.webarch.srmobile.activities.sub.views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import com.webarch.srmobile.activities.sub.views.fragment.Fragment;

/**
 * @author Manoj Khanna
 */

public class ViewPager extends android.support.v4.view.ViewPager {

    private ViewPagerAdapter viewPagerAdapter;

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        viewPagerAdapter = (ViewPagerAdapter) adapter;
    }

    public Fragment getCurrentFragment() {
        return (Fragment) viewPagerAdapter.getFragment(getCurrentItem());
    }

}
