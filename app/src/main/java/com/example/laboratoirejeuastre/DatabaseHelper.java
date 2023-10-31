package com.example.laboratoirejeuastre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "solarSystem.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PLANETS = "planets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PLANETS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SIZE + " INTEGER, " +
                    COLUMN_STATUS + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_X + " REAL, " +
                    COLUMN_Y + " REAL" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANETS);
        onCreate(db);
    }
}
