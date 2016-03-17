package com.webarch.srmobile.activities.sub.views.httpfragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.views.fragment.Fragment;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.sqlitehelpers.SettingsSqliteHelper;
import com.webarch.srmobile.utils.animation.AnimationObject;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.views.Text;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public abstract class HttpFragment extends Fragment {

    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private Text errorMessageText;
    private ScrollView contentScrollView;
    private HttpTask<?> httpTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.sub_http_fragment, container, false);

        progressBar = (ProgressBar) layout.findViewById(R.id.sub_http_fragment_progress_bar);
        errorLayout = (LinearLayout) layout.findViewById(R.id.sub_http_fragment_error);
        errorMessageText = (Text) layout.findViewById(R.id.sub_http_fragment_error_message);
        contentScrollView = (ScrollView) layout.findViewById(R.id.sub_http_fragment_content_scroll);

        final View contentView = getContentView(inflater);

        layout.findViewById(R.id.sub_http_fragment_retry).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                httpTask = onLoadOnlineContent(contentView);
                httpTask.execute();
            }

        });

        int border3 = (int) getResources().getDimension(R.dimen.border_3);
        contentView.setPadding(contentView.getPaddingLeft() + border3,
                contentView.getPaddingTop() + border3,
                contentView.getPaddingRight() + border3,
                contentView.getPaddingBottom() + border3);
        contentScrollView.addView(contentView);

        FragmentActivity activity = getActivity();
        SettingsSqliteHelper settingsSqliteHelper = new SettingsSqliteHelper(activity);
        long refreshInterval = getRefreshInterval();
        boolean canRefresh = refreshInterval != -1 && System.currentTimeMillis() >= settingsSqliteHelper.readHttpFragmentLastRefreshTime(this) + refreshInterval;
        settingsSqliteHelper.close();
        if (canRefresh) {
            NetworkInfo networkInfo = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                onRefresh();

                httpTask = onLoadOnlineContent(contentView);
            } else if (!onLoadOfflineContent(contentView)) {
                httpTask = onLoadOnlineContent(contentView);
            }
        } else if (!onLoadOfflineContent(contentView)) {
            httpTask = onLoadOnlineContent(contentView);
        }

        if (!(this instanceof ChildHttpFragment)) {
            createChildFragmentContainerLayout(layout);
        }

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (httpTask != null && httpTask.getStatus() == AsyncTask.Status.PENDING) {
            httpTask.execute();
        }
    }

    @Override
    public void onPause() {
        if (httpTask != null && httpTask.getStatus() == AsyncTask.Status.RUNNING) {
            httpTask.cancel(true);
        }

        super.onPause();
    }

    protected abstract boolean onLoadOfflineContent(View contentView);

    protected abstract HttpTask<?> onLoadOnlineContent(View contentView);

    protected long getRefreshInterval() {
        return -1;
    }

    protected void onRefresh() {
    }

    protected abstract class HttpTask<Result> extends AsyncTask<Void, Void, Result> {

        private AnimationObject crossFadeAnimationObject;
        private Exception exception;

        @SuppressWarnings("unchecked")
        @Override
        protected final Result doInBackground(Void... params) {
            try {
                return onExecute();
            } catch (IOException | ParserException ex) {
                Log.e(HttpTask.class.getName(), ex.toString());

                exception = ex;

                if (ex instanceof ParserException) {
                    //TODO: Run exception handler here
                }
            }
            return null;
        }

        @Override
        protected final void onPreExecute() {
            super.onPreExecute();

            if (crossFadeAnimationObject != null) {
                crossFadeAnimationObject.end();
            }
            crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{progressBar},
                    new View[]{errorLayout, contentScrollView}, 0, null);

            onBeforeExecute();
        }

        @Override
        protected final void onPostExecute(Result result) {
            super.onPostExecute(result);

            if (exception == null) {
                SettingsSqliteHelper settingsSqliteHelper = new SettingsSqliteHelper(getActivity());
                settingsSqliteHelper.writeHttpFragment(HttpFragment.this);
                settingsSqliteHelper.close();

                onAfterExecute(result);

                if (crossFadeAnimationObject != null) {
                    crossFadeAnimationObject.end();
                }
                crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{contentScrollView},
                        new View[]{progressBar}, 0, null);
            } else {
                if (exception instanceof IOException) {
                    errorMessageText.setText("Connection unavailable!");
                } else {
                    errorMessageText.setText("Data unavailable!");
                }

                if (crossFadeAnimationObject != null) {
                    crossFadeAnimationObject.end();
                }
                crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{errorLayout},
                        new View[]{progressBar}, 0, null);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            errorMessageText.setText("Connection unavailable!");

            if (crossFadeAnimationObject != null) {
                crossFadeAnimationObject.end();
            }
            crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{errorLayout},
                    new View[]{progressBar}, 0, null);
        }

        protected void onBeforeExecute() {
        }

        protected abstract Result onExecute() throws IOException, ParserException;

        protected void onAfterExecute(Result result) {
        }

    }

}
