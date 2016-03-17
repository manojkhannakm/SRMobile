package com.webarch.srmobile.activities.sub.about.views;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.webarch.srmobile.activities.sub.views.httpfragment.HttpFragment;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.views.Text;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class SRMFragment extends HttpFragment {

    @Override
    protected View getContentView(LayoutInflater layoutInflater) {
        FragmentActivity activity = getActivity();
        LinearLayout layout = new LinearLayout(activity);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showChildFragment(new TestFragment());
            }

        });

        Text text = new Text(activity, null);
        text.setText("Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing\n" +
                "Testing");
        layout.addView(text);

        return layout;
    }

    @Override
    protected boolean onLoadOfflineContent(View contentView) {
        return false;
    }

    @Override
    protected HttpTask<?> onLoadOnlineContent(View contentView) {
        return new HttpTask<Object>() {

            @Override
            protected Object onExecute() throws IOException, ParserException {
                return null;
            }

        };
    }

}
