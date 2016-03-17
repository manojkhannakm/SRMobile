package com.webarch.srmobile.sqlitehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.webarch.srmobile.activities.sub.views.httpfragment.HttpFragment;

/**
 * @author Manoj Khanna
 */

public class SettingsSqliteHelper extends SQLiteOpenHelper {

    public SettingsSqliteHelper(Context context) {
        super(context, "settings", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE httpFragments (name TEXT PRIMARY KEY, lastRefreshTime INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void writeHttpFragment(HttpFragment httpFragment) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put("name", ((Object) httpFragment).getClass().getName());
        contentValues.put("lastRefreshTime", System.currentTimeMillis());
        getWritableDatabase().insertWithOnConflict("httpFragments", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long readHttpFragmentLastRefreshTime(HttpFragment httpFragment) {
        Cursor cursor = getReadableDatabase().query("httpFragments", new String[]{"lastRefreshTime"}, "name = '" + ((Object) httpFragment).getClass().getName() + "'", null, null, null, null);
        if (!cursor.moveToFirst()) {
            return -1;
        }
        return cursor.getLong(0);
    }

    public void erase() {
        getWritableDatabase().delete("httpFragments", null, null);
    }

}
