package github.com.foodactioncommitteecookbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 * SQLiteOpenHelper implementation for interacting with the recipe cache.
 */
public class CookbookDb extends SQLiteOpenHelper {

    private static CookbookDb INSTANCE;

    public static CookbookDb create(final Context context) {
        INSTANCE = new CookbookDb(context);
        return INSTANCE;
    }

    public static CookbookDb instance() {
        return INSTANCE;
    }

    private CookbookDb(final Context context) {
        super(context, CookbookContract.DATABASE_NAME, null, CookbookContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CookbookContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over.
        db.execSQL(CookbookContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Just do the same thing as an upgrade; drop and recreate the database.
        onUpgrade(db, oldVersion, newVersion);
    }

    //----------------------------------------------------------------------------------------------
    // Query Helpers
    //----------------------------------------------------------------------------------------------

    public Date getLastModifiedDate() {
        SQLiteDatabase db = getReadableDatabase();
        return null;
    }

    public void insertAll(final List<Recipe> recipes) {

    }
}
