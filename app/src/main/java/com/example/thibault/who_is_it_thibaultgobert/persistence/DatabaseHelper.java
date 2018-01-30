package com.example.thibault.who_is_it_thibaultgobert.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract.*;

/**
 * Created by Thibault on 28/01/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    // Database Name
    private static final String DATABASE_NAME = "guesswho.db";
    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE_GRID = "CREATE TABLE "
            + CharacterEntry.TABLE_NAME + "("
            + CharacterEntry._ID + " INTEGER PRIMARY KEY,"
            + CharacterEntry.COLUMN_NAME + " TEXT,"
            + CharacterEntry.COLUMN_DESCRIPTION + " TEXT,"
            + CharacterEntry.COLUMN_IMAGE + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GRID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CharacterEntry.TABLE_NAME);
        onCreate(db);
    }

}
