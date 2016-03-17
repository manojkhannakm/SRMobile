package com.webarch.srmobile.sqlitehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Manoj Khanna
 */

public class EvarsitySqliteHelper extends SQLiteOpenHelper {

    public static final int PROFILE_REG_NO_INDEX = 0;
    public static final int PROFILE_NAME_INDEX = 1;
    public static final int PROFILE_FATHERS_NAME_INDEX = 2;
    public static final int PROFILE_PICTURE_INDEX = 3;
    public static final int PROFILE_DATE_OF_BIRTH_INDEX = 4;
    public static final int PROFILE_SEX_INDEX = 5;
    public static final int PROFILE_BLOOD_GROUP_INDEX = 6;
    public static final int PROFILE_OFFICE_INDEX = 7;
    public static final int PROFILE_COURSE_INDEX = 8;
    public static final int PROFILE_SEMESTER_INDEX = 9;
    public static final int PROFILE_SECTION_INDEX = 10;
    public static final int PROFILE_ADDRESS_INDEX = 11;
    public static final int PROFILE_PINCODE_INDEX = 12;
    public static final int PROFILE_EMAIL_INDEX = 13;
    public static final int PROFILE_VALIDITY_INDEX = 14;

    public static final int SUBJECTS_CODE_INDEX = 0;
    public static final int SUBJECTS_NAME_INDEX = 1;
    public static final int SUBJECTS_CREDIT_INDEX = 2;

    public EvarsitySqliteHelper(Context context) {
        super(context, "evarsity", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE profile (regNo TEXT PRIMARY KEY, name TEXT, fathersName TEXT, picture BLOB, dateOfBirth TEXT, sex TEXT, bloodGroup TEXT, office TEXT, course TEXT, semester TEXT, section TEXT, address TEXT, pincode TEXT, email TEXT, validity TEXT)");
        db.execSQL("CREATE TABLE subjects (code TEXT, name TEXT, credit TEXT, curriculum TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void writeProfile(String regNo, String name, String fathersName, byte[] picture, String dateOfBirth, String sex, String bloodGroup, String office, String course, String semester, String section, String address, String pincode, String email, String validity) {
        ContentValues contentValues = new ContentValues(15);
        contentValues.put("regNo", regNo);
        contentValues.put("name", name);
        contentValues.put("fathersName", fathersName);
        contentValues.put("picture", picture);
        contentValues.put("dateOfBirth", dateOfBirth);
        contentValues.put("sex", sex);
        contentValues.put("bloodGroup", bloodGroup);
        contentValues.put("office", office);
        contentValues.put("course", course);
        contentValues.put("semester", semester);
        contentValues.put("section", section);
        contentValues.put("address", address);
        contentValues.put("pincode", pincode);
        contentValues.put("email", email);
        contentValues.put("validity", validity);
        getWritableDatabase().insertWithOnConflict("profile", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor readProfile() {
        Cursor cursor = getReadableDatabase().query("profile", null, null, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        return cursor;
    }

    public void writeSubject(String code, String name, String credit) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("code", code);
        contentValues.put("name", name);
        contentValues.put("credit", credit);
        getWritableDatabase().insert("subjects", null, contentValues);
    }

    public Cursor readSubjects() {
        Cursor cursor = getReadableDatabase().query("subjects", null, null, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        return cursor;
    }

    public void eraseSubjects() {
        getWritableDatabase().delete("subjects", null, null);
    }

    public void writeSubjectCurriculum(String code, String curriculum) {
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("curriculum", curriculum);
        getWritableDatabase().update("subjects", contentValues, "code = '" + code + "'", null);
    }

    public String readSubjectCurriculum(String code) {
        Cursor cursor = getReadableDatabase().query("subjects", new String[]{"curriculum"}, "code = '" + code + "'", null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        return cursor.getString(0);
    }

    public void eraseCurriculum(String code) {
        getWritableDatabase().delete("curriculum", "code = '" + code + "'", null);
    }

    public void erase() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("profile", null, null);
        database.delete("subjects", null, null);
    }

}
