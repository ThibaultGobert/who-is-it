package com.example.thibault.who_is_it_thibaultgobert.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import static com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract.CONTENT_AUTHORITY;
import static com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract.CharacterEntry.TABLE_NAME;
import static com.example.thibault.who_is_it_thibaultgobert.persistence.CharacterContract.PATH_CHARACTERS;


/**
 * Created by Thibault on 28/01/2018.
 */

public class CharacterProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CHARACTERS = 1;
    private static final int CHARACTERS_ID = 2;
    private static final int CHARACTERS_NAME = 3;

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CHARACTERS, CHARACTERS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CHARACTERS + "/#", CHARACTERS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CHARACTERS + "/*", CHARACTERS_NAME);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CHARACTERS:
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CHARACTERS_ID:
                selection = CharacterContract.CharacterEntry._ID;
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch(uriMatcher.match(uri)){
            case CHARACTERS: return insertRecord(uri, contentValues, TABLE_NAME);
            default: throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues contentValues, String tableName){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId = database.insert(tableName, null, contentValues);
        if (rowId == -1 ){
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch(uriMatcher.match(uri)){
            case CHARACTERS : return deleteRecord(uri, selection, selectionArgs);
            case CHARACTERS_ID:
                selection = CharacterContract.CharacterEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri, selection, selectionArgs);
            case CHARACTERS_NAME:
                selection = CharacterContract.CharacterEntry.COLUMN_NAME + " = ?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                deleteRecord(uri, selection, selectionArgs);
            default: throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    private int deleteRecord(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
        if (rowsDeleted > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case CHARACTERS:
                return updateRecord(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    private int updateRecord(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int rowsUpdated = database.update(TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
