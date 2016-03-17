package com.webarch.srmobile.activities.sub.evarsity.views.subjects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.views.httpfragment.ChildHttpFragment;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.sqlitehelpers.EvarsitySqliteHelper;
import com.webarch.srmobile.views.Text;

import java.io.IOException;

//import com.webarch.srmobile.parsers.srmobile.CurriculumParser;

/**
 * @author Manoj Khanna
 */

public class CurriculumFragment extends ChildHttpFragment {

    private String code, name;

    @Override
    protected String getTitle() {
        return "Curriculum - " + name;
    }

    @SuppressLint("InflateParams")
    @Override
    protected View getContentView(LayoutInflater layoutInflater) {
        Bundle arguments = getArguments();
        code = arguments.getString("code");
        name = arguments.getString("name");

        return layoutInflater.inflate(R.layout.evarsity_subjects_curriculum, null);
    }

    @Override
    protected boolean onLoadOfflineContent(View contentView) {
        EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(getActivity());
        String curriculum = evarsitySqliteHelper.readSubjectCurriculum(code);
        if (curriculum != null) {
            ((Text) contentView.findViewById(R.id.evarsity_subjects_curriculum)).setText(Html.fromHtml(curriculum));
            evarsitySqliteHelper.close();
            return true;
        }
        evarsitySqliteHelper.close();
        return false;
    }

    @Override
    protected HttpTask<?> onLoadOnlineContent(final View contentView) {
        return new HttpTask<String>() {

            @Override
            protected String onExecute() throws IOException, ParserException {
//                CurriculumParser curriculumParser = new CurriculumParser(code);
//                curriculumParser.execute();
//
//                if (isCancelled()) {
//                    return null;
//                }
//
//                String curriculum = curriculumParser.getMatchListMap().get("curriculum").get(0).get(0);
//                EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(getActivity());
//                evarsitySqliteHelper.writeSubjectCurriculum(code, curriculum);
//                evarsitySqliteHelper.close();
//                return curriculum;
                return null;
            }

            @Override
            protected void onAfterExecute(String curriculum) {
                ((Text) contentView.findViewById(R.id.evarsity_subjects_curriculum)).setText(Html.fromHtml(curriculum));
            }

        };
    }

}
