package com.webarch.srmobile.activities.sub.evarsity.views;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.webarch.srmobile.R;
import com.webarch.srmobile.activities.sub.views.httpfragment.HttpFragment;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.parsers.ParserException;
import com.webarch.srmobile.parsers.ProfileParser;
import com.webarch.srmobile.sqlitehelpers.EvarsitySqliteHelper;
import com.webarch.srmobile.utils.AndroidUtils;
import com.webarch.srmobile.views.Text;

import java.io.IOException;

/**
 * @author Manoj Khanna
 */

public class ProfileFragment extends HttpFragment {

    @SuppressLint("InflateParams")
    @Override
    protected View getContentView(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.evarsity_profile, null);
    }

    @Override
    protected boolean onLoadOfflineContent(View contentView) {
        EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(getActivity());
        Cursor cursor = evarsitySqliteHelper.readProfile();
        if (cursor != null) {
            String fathersName = cursor.getString(EvarsitySqliteHelper.PROFILE_FATHERS_NAME_INDEX);
            if (fathersName != null) {
                setContent(contentView,
                        cursor.getBlob(EvarsitySqliteHelper.PROFILE_PICTURE_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_REG_NO_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_NAME_INDEX),
                        fathersName,
                        cursor.getString(EvarsitySqliteHelper.PROFILE_DATE_OF_BIRTH_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_SEX_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_BLOOD_GROUP_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_OFFICE_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_COURSE_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_SEMESTER_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_SECTION_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_ADDRESS_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_PINCODE_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_EMAIL_INDEX),
                        cursor.getString(EvarsitySqliteHelper.PROFILE_VALIDITY_INDEX));
                evarsitySqliteHelper.close();
                return true;
            }
        }
        evarsitySqliteHelper.close();
        return false;
    }

    @Override
    protected HttpTask<?> onLoadOnlineContent(final View contentView) {
        return new HttpTask<Object[]>() {

            @Override
            protected void onBeforeExecute() {
                if (!User.isSignedIn()) {
                    cancel(true);
                }
            }

            @Override
            protected Object[] onExecute() throws IOException, ParserException {
                FragmentActivity activity = getActivity();
                EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(activity);
                Cursor cursor = evarsitySqliteHelper.readProfile();
                String regNo = cursor.getString(EvarsitySqliteHelper.PROFILE_REG_NO_INDEX),
                        name = cursor.getString(EvarsitySqliteHelper.PROFILE_NAME_INDEX),
                        fathersName,
                        dateOfBirth = cursor.getString(EvarsitySqliteHelper.PROFILE_DATE_OF_BIRTH_INDEX),
                        sex, bloodGroup,
                        office = cursor.getString(EvarsitySqliteHelper.PROFILE_OFFICE_INDEX),
                        course = cursor.getString(EvarsitySqliteHelper.PROFILE_COURSE_INDEX),
                        semester = cursor.getString(EvarsitySqliteHelper.PROFILE_SEMESTER_INDEX),
                        section = cursor.getString(EvarsitySqliteHelper.PROFILE_SECTION_INDEX),
                        address, pincode, email, validity;
                byte[] picture = cursor.getBlob(EvarsitySqliteHelper.PROFILE_PICTURE_INDEX);
                evarsitySqliteHelper.close();

                if (isCancelled()) {
                    return null;
                }

                ProfileParser profileParser = new ProfileParser(User.getUser().getCookie());
                profileParser.execute();

                if (isCancelled()) {
                    return null;
                }

                fathersName = profileParser.getFathersName();
                sex = profileParser.getSex();
                bloodGroup = profileParser.getBloodGroup();
                address = profileParser.getAddress();
                pincode = profileParser.getPincode();
                email = profileParser.getEmail();
                validity = profileParser.getValidity();

                evarsitySqliteHelper = new EvarsitySqliteHelper(activity);
                evarsitySqliteHelper.writeProfile(regNo, name, fathersName, picture, dateOfBirth, sex, bloodGroup, office, course, semester, section, address, pincode, email, validity);
                evarsitySqliteHelper.close();
                return new Object[]{picture, regNo, name, fathersName, picture, dateOfBirth, sex, bloodGroup, office, course, semester, section, address, pincode, email, validity};
            }

            @Override
            protected void onAfterExecute(Object[] profile) {
                setContent(contentView,
                        (byte[]) profile[0],
                        (String) profile[1],
                        (String) profile[2],
                        (String) profile[3],
                        (String) profile[4],
                        (String) profile[5],
                        (String) profile[6],
                        (String) profile[7],
                        (String) profile[8],
                        (String) profile[9],
                        (String) profile[10],
                        (String) profile[11],
                        (String) profile[12],
                        (String) profile[13],
                        (String) profile[14]);
            }

        };
    }

    private void setContent(View contentView, byte[] picture, String regNo, String name, String fathersName, String dateOfBirth, String sex, String bloodGroup, String office, String course, String semester, String section, String address, String pincode, String email, String validity) {
        ((ImageView) contentView.findViewById(R.id.evarsity_profile_picture)).setImageBitmap(AndroidUtils.createCircleBitmap(getActivity(), picture, 75));
        ((Text) contentView.findViewById(R.id.evarsity_profile_reg_no)).setText(Html.fromHtml("<b>Register no.: </b>" + regNo));
        ((Text) contentView.findViewById(R.id.evarsity_profile_name)).setText(Html.fromHtml("<b>Name: </b>" + name));
        ((Text) contentView.findViewById(R.id.evarsity_profile_fathers_name)).setText(Html.fromHtml("<b>Father's name: </b>" + fathersName));
        ((Text) contentView.findViewById(R.id.evarsity_profile_date_of_birth)).setText(Html.fromHtml("<b>Date of birth: </b>" + dateOfBirth));
        ((Text) contentView.findViewById(R.id.evarsity_profile_sex)).setText(Html.fromHtml("<b>Sex: </b>" + sex));
        ((Text) contentView.findViewById(R.id.evarsity_profile_blood_group)).setText(Html.fromHtml("<b>Blood group: </b>" + bloodGroup));
        ((Text) contentView.findViewById(R.id.evarsity_profile_office)).setText(Html.fromHtml("<b>Office: </b>" + office));
        ((Text) contentView.findViewById(R.id.evarsity_profile_course)).setText(Html.fromHtml("<b>Course: </b>" + course));
        ((Text) contentView.findViewById(R.id.evarsity_profile_semester)).setText(Html.fromHtml("<b>Semester: </b>" + semester));
        ((Text) contentView.findViewById(R.id.evarsity_profile_section)).setText(Html.fromHtml("<b>Section: </b>" + section));
        ((Text) contentView.findViewById(R.id.evarsity_profile_address)).setText(Html.fromHtml("<b>Address: </b>" + address));
        ((Text) contentView.findViewById(R.id.evarsity_profile_pincode)).setText(Html.fromHtml("<b>Pincode: </b>" + pincode));
        ((Text) contentView.findViewById(R.id.evarsity_profile_email)).setText(Html.fromHtml("<b>E-mail: </b>" + email));
        ((Text) contentView.findViewById(R.id.evarsity_profile_validity)).setText(Html.fromHtml("<b>Validity: </b>" + validity));
    }

}
