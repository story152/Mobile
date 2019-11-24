package com.example.mobileprogrammingtermproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBName = "id_info.db";
    private static final int DBVer = 1;

    public DBHelper(Context context) {
        super(context, DBName, null, DBVer);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE id_info ( _id INTEGER PRIMARY KEY AUTOINCREMENT, ID TEXT, PW TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS id_info");
        onCreate(db);
    }
}
