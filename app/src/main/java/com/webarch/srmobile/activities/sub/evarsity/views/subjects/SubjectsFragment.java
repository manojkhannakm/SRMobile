package com.webarch.srmobile.activities.sub.evarsity.views.subjects;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.views.TableRowButton;
import com.webarch.srmobile.activities.sub.views.httpfragment.HttpFragment;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.parsers.SubjectsParser;
import com.webarch.srmobile.sqlitehelpers.EvarsitySqliteHelper;
import com.webarch.srmobile.views.Text;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class SubjectsFragment extends HttpFragment {

    @SuppressLint("InflateParams")
    @Override
    protected View getContentView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.evarsity_subjects, null);
    }

    @Override
    protected boolean onLoadOfflineContent(View contentView) {
        FragmentActivity activity = getActivity();
        EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(activity);
        Cursor cursor = evarsitySqliteHelper.readSubjects();
        if (cursor != null) {
            ((Text) contentView.findViewById(R.id.evarsity_subjects_code)).setText(Html.fromHtml("<b>Code</b>"));
            ((Text) contentView.findViewById(R.id.evarsity_subjects_name)).setText(Html.fromHtml("<b>Name</b>"));
            ((Text) contentView.findViewById(R.id.evarsity_subjects_credit)).setText(Html.fromHtml("<b>Credit</b>"));

            LayoutInflater layoutInflater = activity.getLayoutInflater();
            do {
                addSubject(layoutInflater, (TableLayout) contentView,
                        cursor.getString(EvarsitySqliteHelper.SUBJECTS_CODE_INDEX),
                        cursor.getString(EvarsitySqliteHelper.SUBJECTS_NAME_INDEX), cursor.getString(EvarsitySqliteHelper.SUBJECTS_CREDIT_INDEX));
            } while (cursor.moveToNext());
            evarsitySqliteHelper.close();
            return true;
        }
        evarsitySqliteHelper.close();
        return false;
    }

    @Override
    protected HttpTask<?> onLoadOnlineContent(final View contentView) {
        return new HttpTask<ArrayList<ArrayList<String>>>() {

            @Override
            protected ArrayList<ArrayList<String>> onExecute() throws IOException, ParserException {
                SubjectsParser subjectsParser = new SubjectsParser();
                subjectsParser.execute();

                if (isCancelled()) {
                    return null;
                }

                ArrayList<ArrayList<String>> subjects = subjectsParser.getSubjects();
                EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(getActivity());
                for (ArrayList<String> subjectsResultList : subjects) {
                    evarsitySqliteHelper.writeSubject(subjectsResultList.get(0), subjectsResultList.get(1), subjectsResultList.get(2));
                }
                evarsitySqliteHelper.close();
                return subjects;
            }

            @Override
            protected void onAfterExecute(ArrayList<ArrayList<String>> subjectsResultListList) {
                ((Text) contentView.findViewById(R.id.evarsity_subjects_code)).setText(Html.fromHtml("<b>Code</b>"));
                ((Text) contentView.findViewById(R.id.evarsity_subjects_name)).setText(Html.fromHtml("<b>Name</b>"));
                ((Text) contentView.findViewById(R.id.evarsity_subjects_credit)).setText(Html.fromHtml("<b>Credit</b>"));

                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                for (ArrayList<String> subjectsResultList : subjectsResultListList) {
                    addSubject(layoutInflater, (TableLayout) contentView, subjectsResultList.get(0), subjectsResultList.get(1), subjectsResultList.get(2));
                }
            }

        };
    }

    @Override
    protected long getRefreshInterval() {
        return 12 * 60 * 60 * 1000;
    }

    @Override
    protected void onRefresh() {
        EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(getActivity());
        evarsitySqliteHelper.eraseSubjects();
        evarsitySqliteHelper.close();
    }

    private void addSubject(LayoutInflater layoutInflater, TableLayout tableLayout, final String code, final String name, String credit) {
        TableRowButton tableRowButton = (TableRowButton) layoutInflater.inflate(R.layout.evarsity_subjects_subject, tableLayout, false);
        tableRowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CurriculumFragment curriculumFragment = new CurriculumFragment();
                Bundle argumentsBundle = new Bundle(2);
                argumentsBundle.putString("code", code);
                argumentsBundle.putString("name", name);
                curriculumFragment.setArguments(argumentsBundle);
                showChildFragment(curriculumFragment);
            }

        });

        ((Text) tableRowButton.findViewById(R.id.evarsity_subjects_subject_code)).setText(code);
        ((Text) tableRowButton.findViewById(R.id.evarsity_subjects_subject_name)).setText(name);
        ((Text) tableRowButton.findViewById(R.id.evarsity_subjects_subject_credit)).setText(credit);

        tableLayout.addView(tableRowButton);
    }

}
