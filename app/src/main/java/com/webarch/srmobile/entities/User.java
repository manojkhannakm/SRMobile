package com.webarch.srmobile.entities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.webarch.srmobile.sqlitehelpers.EvarsitySqliteHelper;
import com.webarch.srmobile.sqlitehelpers.SettingsSqliteHelper;
import com.webarch.srmobile.sqlitehelpers.UsersSqliteHelper;
import com.webarch.srmobile.utils.AndroidUtils;

/**
 * @author Manoj Khanna
 */

public class User {

    private static User user;
    private String cookie, regNo, name, dateOfBirth, office, course, semester, section;
    private Bitmap pictureBitmap;

    private User(Context context, String cookie) {
        this.cookie = cookie;

        UsersSqliteHelper usersSqliteHelper = new UsersSqliteHelper(context);
        regNo = usersSqliteHelper.readCredentials()[0];

        Cursor cursor = usersSqliteHelper.readUser(regNo);
        name = cursor.getString(UsersSqliteHelper.USERS_NAME_INDEX);
        pictureBitmap = AndroidUtils.createCircleBitmap(context, cursor.getBlob(UsersSqliteHelper.USERS_PICTURE_INDEX), 50);
        dateOfBirth = cursor.getString(UsersSqliteHelper.USERS_DATE_OF_BIRTH_INDEX);
        office = cursor.getString(UsersSqliteHelper.USERS_OFFICE_INDEX);
        course = cursor.getString(UsersSqliteHelper.USERS_COURSE_INDEX);
        semester = cursor.getString(UsersSqliteHelper.USERS_SEMESTER_INDEX);
        section = cursor.getString(UsersSqliteHelper.USERS_SECTION_INDEX);

        usersSqliteHelper.close();
    }

    public static void create(Context context, String cookie) {
        user = new User(context, cookie);
    }

    public static boolean isSignedIn() {
        return user != null;
    }

    public static void destroy(Context context) {
        UsersSqliteHelper usersSqliteHelper = new UsersSqliteHelper(context);
        usersSqliteHelper.erase();
        usersSqliteHelper.close();

        EvarsitySqliteHelper evarsitySqliteHelper = new EvarsitySqliteHelper(context);
        evarsitySqliteHelper.erase();
        evarsitySqliteHelper.close();

        SettingsSqliteHelper settingsSqliteHelper = new SettingsSqliteHelper(context);
        settingsSqliteHelper.erase();
        settingsSqliteHelper.close();

        //TODO: Uncomment later
//        context.getSharedPreferences(SignInDialogFragment.class.getName(), Context.MODE_PRIVATE).edit()
//                .remove("password")
//                .commit();

        user = null;
    }

    public static User getUser() {
        return user;
    }

    public String getCookie() {
        return cookie;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public Bitmap getPictureBitmap() {
        return pictureBitmap;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getOffice() {
        return office;
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public String getSection() {
        return section;
    }

}
