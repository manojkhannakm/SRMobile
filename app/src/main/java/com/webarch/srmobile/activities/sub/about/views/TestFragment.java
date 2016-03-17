package com.webarch.srmobile.activities.sub.about.views;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.webarch.srmobile.activities.sub.views.httpfragment.ChildHttpFragment;
import com.webarch.srmobile.parsers.ParserException;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class TestFragment extends ChildHttpFragment {

    @Override
    protected String getTitle() {
        return "Test";
    }

    @Override
    protected View getContentView(LayoutInflater layoutInflater) {
        return new LinearLayout(getActivity());
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
                throw new IOException("Testing...");
            }

        };
    }

}
