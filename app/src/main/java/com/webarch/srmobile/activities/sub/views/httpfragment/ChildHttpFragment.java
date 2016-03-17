package com.webarch.srmobile.activities.sub.views.httpfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.views.fragment.Fragment;
import com.webarch.srmobile.utils.AndroidUtils;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.views.Text;

/**
 * @author Manoj Khanna
 */

public abstract class ChildHttpFragment extends HttpFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout httpFragmentLayout = (RelativeLayout) super.onCreateView(inflater, container, savedInstanceState),
                layout = (RelativeLayout) inflater.inflate(R.layout.sub_child_fragment, container, false);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }

        });

        ((Text) layout.findViewById(R.id.sub_child_fragment_title_bar_title)).setText(getTitle());

        layout.findViewById(R.id.sub_child_fragment_title_bar_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((Fragment) getParentFragment()).hideChildFragment();
            }

        });

        RelativeLayout.LayoutParams httpFragmentLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        httpFragmentLayoutParams.addRule(RelativeLayout.BELOW, R.id.sub_child_fragment_title_bar);
        layout.addView(httpFragmentLayout, httpFragmentLayoutParams);

        return layout;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            view.setVisibility(View.INVISIBLE);

            AndroidUtils.postLayout(view, new Runnable() {

                @Override
                public void run() {
                    AnimationUtils.zoomIn(view, false, 0, null, null);
                }

            });
        }
    }

    protected abstract String getTitle();

}
