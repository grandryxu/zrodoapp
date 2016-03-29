package com.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.cache.OpenUrlCache;

/**
 * Created by grandry.xu on 15-9-23.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION=2;
    public static final String DB_NAME="zroddetec.db";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           new OpenUrlCache().createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
