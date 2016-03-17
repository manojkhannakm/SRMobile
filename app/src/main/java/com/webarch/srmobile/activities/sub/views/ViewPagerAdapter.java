package com.webarch.srmobile.activities.sub.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.webarch.srmobile.activities.sub.SubActivity;

/**
 * @author Manoj Khanna
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] titles;
    private Class[] subFragmentClasses;
    private SparseArray<Fragment> fragmentArray;

    public ViewPagerAdapter(SubActivity subActivity) {
        super(subActivity.getSupportFragmentManager());

        context = subActivity;
        titles = subActivity.getFragmentTitles();
        subFragmentClasses = subActivity.getFragmentClasses();
        fragmentArray = new SparseArray<>(titles.length);
    }

    @Override
    public Fragment getItem(final int position) {
        return Fragment.instantiate(context, subFragmentClasses[position].getName());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentArray.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentArray.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public Fragment getFragment(int index) {
        return fragmentArray.get(index);
    }

}
