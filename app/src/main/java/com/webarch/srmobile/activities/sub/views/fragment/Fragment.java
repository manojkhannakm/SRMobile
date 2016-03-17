package com.webarch.srmobile.activities.sub.views.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.webarch.srmobile.R;
import com.webarch.srmobile.utils.animation.AnimationListenerAdapter;
import com.webarch.srmobile.utils.animation.AnimationObject;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.utils.animation.ColorAnimation;

/**
 * @author Manoj Khanna
 */

public abstract class Fragment extends android.support.v4.app.Fragment {

    private LinearLayout childFragmentContainerLayout;
    private AnimationObject childFragmentContainerAnimationObject;
    private boolean hidingChildFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.sub_fragment, container, false);

        View contentView = getContentView(inflater);
        int border3 = (int) getResources().getDimension(R.dimen.border_3);
        contentView.setPadding(contentView.getPaddingLeft() + border3,
                contentView.getPaddingTop() + border3,
                contentView.getPaddingRight() + border3,
                contentView.getPaddingBottom() + border3);
        ((ScrollView) layout.findViewById(R.id.sub_fragment_content_scroll)).addView(contentView, 0);

        if (!(this instanceof ChildFragment)) {
            createChildFragmentContainerLayout(layout);
        }

        return layout;
    }

    protected abstract View getContentView(LayoutInflater layoutInflater);

    protected void createChildFragmentContainerLayout(RelativeLayout layout) {
        childFragmentContainerLayout = new LinearLayout(getActivity());
        childFragmentContainerLayout.setId(R.id.sub_fragment_child_fragment_container);
        Resources resources = getResources();
        int border = (int) (resources.getDimension(R.dimen.border_1) + resources.getDimension(R.dimen.border_2));
        childFragmentContainerLayout.setPadding(border, border, border, border);

        if (getChildFragmentManager().findFragmentByTag(ChildFragment.class.getName()) == null) {
            childFragmentContainerLayout.setVisibility(View.INVISIBLE);
        } else {
            childFragmentContainerLayout.setBackgroundResource(R.color.semi_black);
        }

        childFragmentContainerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideChildFragment();
            }

        });
        layout.addView(childFragmentContainerLayout);
    }

    protected void showChildFragment(Fragment childFragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.sub_fragment_child_fragment_container, childFragment, ChildFragment.class.getName())
                .addToBackStack(null)
                .commit();

        if (childFragmentContainerAnimationObject != null) {
            childFragmentContainerAnimationObject.cancel();
        }
        childFragmentContainerAnimationObject = AnimationUtils.changeColor(childFragmentContainerLayout,
                Color.TRANSPARENT, getResources().getColor(R.color.semi_black),
                0, null,
                new ColorAnimation.OnColorChangeListener() {

                    @Override
                    public void onColorChanged(int color) {
                        childFragmentContainerLayout.setBackgroundColor(color);
                    }

                },
                new AnimationListenerAdapter() {

                    @Override
                    public void onAnimationStart() {
                        childFragmentContainerLayout.setVisibility(View.VISIBLE);
                    }

                }
        );
    }

    public boolean hideChildFragment() {
        if (hidingChildFragment) {
            return false;
        }

        final FragmentManager childFragmentManager = getChildFragmentManager();
        android.support.v4.app.Fragment childFragment = childFragmentManager.findFragmentByTag(ChildFragment.class.getName());
        if (childFragment == null) {
            return false;
        }

        hidingChildFragment = true;

        AnimationUtils.zoomOut(childFragment.getView(), true, 0, null, new AnimationListenerAdapter() {

            @Override
            public void onAnimationEnd() {
                childFragmentManager.popBackStackImmediate();
            }

        });

        AnimationUtils.changeColor(childFragmentContainerLayout, getResources().getColor(R.color.semi_black), Color.TRANSPARENT, 0, null, new ColorAnimation.OnColorChangeListener() {

                    @Override
                    public void onColorChanged(int color) {
                        childFragmentContainerLayout.setBackgroundColor(color);
                    }

                },
                new AnimationListenerAdapter() {

                    @Override
                    public void onAnimationEnd() {
                        childFragmentContainerLayout.setVisibility(View.INVISIBLE);

                        hidingChildFragment = false;
                    }

                }
        );
        return true;
    }

}
