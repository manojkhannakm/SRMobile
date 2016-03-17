package com.webarch.srmobile.entities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.webarch.srmobile.sqlitehelpers.UsersSqliteHelper;
import com.webarch.srmobile.utils.AndroidUtils;

/**
 * @author Manoj Khanna
 */

public class OtherUser {

    private String regNo, name, office, course, semester;
    private Bitmap pictureBitmap;

    public OtherUser(Context context, String regNo) {
        this.regNo = regNo;

        UsersSqliteHelper usersSqliteHelper = new UsersSqliteHelper(context);
        Cursor cursor = usersSqliteHelper.readUser(regNo);
        name = cursor.getString(UsersSqliteHelper.USERS_NAME_INDEX);
        pictureBitmap = AndroidUtils.createCircleBitmap(context, cursor.getBlob(UsersSqliteHelper.USERS_PICTURE_INDEX), 50);
        office = cursor.getString(UsersSqliteHelper.USERS_OFFICE_INDEX);
        course = cursor.getString(UsersSqliteHelper.USERS_COURSE_INDEX);
        semester = cursor.getString(UsersSqliteHelper.USERS_SEMESTER_INDEX);
        usersSqliteHelper.close();
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

    public String getOffice() {
        return office;
    }

    public String getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

}
