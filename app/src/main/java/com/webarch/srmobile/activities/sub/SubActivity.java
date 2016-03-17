package com.webarch.srmobile.activities.sub;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.Activity;
import com.webarch.srmobile.activities.sub.evarsity.EvarsityActivity;
import com.webarch.srmobile.activities.sub.views.SlidingTabLayout;
import com.webarch.srmobile.activities.sub.views.ViewPager;
import com.webarch.srmobile.activities.sub.views.ViewPagerAdapter;
import com.webarch.srmobile.activities.sub.views.fragment.Fragment;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.tasks.AutoSignInAsyncTask;
import com.webarch.srmobile.views.Text;
import com.webarch.srmobile.views.actionbar.ActionBar;
import com.webarch.srmobile.views.quickaccess.QuickAccessPane;
import com.webarch.srmobile.views.quickaccess.UserButton;
import com.webarch.srmobile.views.search.SearchPane;

/**
 * @author Manoj Khanna
 */

public abstract class SubActivity extends Activity {

    private ViewPager viewPager;

    @Override
    public void onBackPressed() {
        Fragment parentFragment = viewPager.getCurrentFragment();
        if (parentFragment == null || !parentFragment.hideChildFragment()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sub);

        drawerLayout = (DrawerLayout) findViewById(R.id.sub_drawer);
        viewPager = (ViewPager) findViewById(R.id.sub_view_pager);
        quickAccessPane = (QuickAccessPane) findViewById(R.id.sub_quick_access_pane);
        userButton = (UserButton) findViewById(R.id.quick_access_pane_user_button);
        searchPane = (SearchPane) findViewById(R.id.sub_search_pane);

        ActionBar actionBar = (ActionBar) findViewById(R.id.sub_action_bar);
        ((Text) actionBar.findViewById(R.id.action_bar_title)).setText(getSubActivityTitle());
        actionBar.setOnClickListener(this);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sub_sliding_tab);
        slidingTabLayout.setViewPager(viewPager);
        Resources resources = getResources();
        slidingTabLayout.setDividerColors(resources.getColor(R.color.light));
        slidingTabLayout.setSelectedIndicatorColors(resources.getColor(R.color.dark));

        userButton.setOnClickListener(this);

        if (!User.isSignedIn() && !(this instanceof EvarsityActivity)) {
            autoSignInAsyncTask = new AutoSignInAsyncTask(this);
            autoSignInAsyncTask.execute();
        }
    }

    protected abstract String getSubActivityTitle();

    public abstract String[] getFragmentTitles();

    public abstract Class[] getFragmentClasses();

}
