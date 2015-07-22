package github.com.foodactioncommitteecookbook.db;

import android.provider.BaseColumns;

/**
 * Contract class for the database schema.
 */
public final class CookbookContract {

    private CookbookContract() {
    }

    // Update this version number if you change anything else in this file.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "FACCookbook.db";

    public static final String SQL_CREATE_ENTRIES = RecipeEntry.CREATE_TABLE;

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + CookbookContract.RecipeEntry.TABLE_NAME;

    public static abstract class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_ID = "recipe_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_FAVOURITE = "is_favourite";
        public static final String COLUMN_SEARCH = "search_items";
        public static final String COLUMN_SEASON = "season";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_MODIFED = "last_modified";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ID + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_FAVOURITE + " INTEGER, " +
                COLUMN_SEARCH + " TEXT, " +
                COLUMN_SEASON + " TEXT, " +
                COLUMN_CREATED + " DATETIME, " +
                COLUMN_MODIFED + " DATETIME " +
                ")";

    }
}
