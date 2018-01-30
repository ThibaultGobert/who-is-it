package com.example.thibault.who_is_it_thibaultgobert.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Thibault on 28/01/2018.
 */

public class CharacterContract {
    public static final String CONTENT_AUTHORITY = "com.example.thibault.provider.who_is_it_thibaultgobert";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CHARACTERS = "characters";

    public static final class CharacterEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CHARACTERS);
        public static final String TABLE_NAME = "characters";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE = "image";
    }
}
