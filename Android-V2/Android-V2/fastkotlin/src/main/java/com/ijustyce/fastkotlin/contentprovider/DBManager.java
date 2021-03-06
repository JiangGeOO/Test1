package com.ijustyce.fastkotlin.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangchun on 16/5/18.
 */
class DBManager extends SQLiteOpenHelper {
    private static int VERSION = 1;
    DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists common(_id integer primary key autoincrement, " +
                "name text, value text, userId text)");
        db.execSQL("create index index_common on common(name)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
