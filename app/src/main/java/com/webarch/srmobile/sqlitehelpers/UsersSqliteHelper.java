package com.webarch.srmobile.sqlitehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Manoj Khanna
 */

public class UsersSqliteHelper extends SQLiteOpenHelper {

    public static final int USERS_REG_NO_INDEX = 0;
    public static final int USERS_NAME_INDEX = 1;
    public static final int USERS_PICTURE_INDEX = 2;
    public static final int USERS_DATE_OF_BIRTH_INDEX = 3;
    public static final int USERS_OFFICE_INDEX = 4;
    public static final int USERS_COURSE_INDEX = 5;
    public static final int USERS_SEMESTER_INDEX = 6;
    public static final int USERS_SECTION_INDEX = 7;

    public UsersSqliteHelper(Context context) {
        super(context, "users", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE credentials (regNo TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE users (regNo TEXT PRIMARY KEY, name TEXT, picture BLOB, dateOfBirth TEXT, office TEXT, course TEXT, semester TEXT, section TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void writeCredentials(String regNo, String password) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("regNo", regNo);
        contentValues.put("password", password);
        getWritableDatabase().insertWithOnConflict("credentials", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public String[] readCredentials() {
        Cursor cursor = getReadableDatabase().query("credentials", null, null, null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        return new String[]{cursor.getString(0), cursor.getString(1)};
    }

    public void writeUser(String regNo, String name, byte[] picture, String dateOfBirth, String office, String course, String semester, String section) {
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("regNo", regNo);
        contentValues.put("name", name);
        contentValues.put("picture", picture);
        contentValues.put("dateOfBirth", dateOfBirth);
        contentValues.put("office", office);
        contentValues.put("course", course);
        contentValues.put("semester", semester);
        contentValues.put("section", section);
        getWritableDatabase().insertWithOnConflict("users", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor readUser(String regNo) {
        Cursor cursor = getReadableDatabase().query("users", null, "regNo = '" + regNo + "'", null, null, null, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        return cursor;
    }

    public void erase() {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues(2);
        contentValues.put("dateOfBirth", (String) null);
        contentValues.put("section", (String) null);
        Cursor cursor = database.query("credentials", new String[]{"regNo"}, null, null, null, null, null);
        cursor.moveToFirst();
        database.update("users", contentValues, "regNo = ?", new String[]{cursor.getString(0)});

        database.delete("credentials", null, null);
    }

}
