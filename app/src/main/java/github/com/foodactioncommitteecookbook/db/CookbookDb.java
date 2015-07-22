package github.com.foodactioncommitteecookbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 * SQLiteOpenHelper implementation for interacting with the recipe cache.
 */
public class CookbookDb extends SQLiteOpenHelper {

    private static final Logger log = LoggerFactory.getLogger(CookbookDb.class);

    private static final DateFormat dateFormat = DateFormat.getDateInstance();

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

        String[] projection = {
                CookbookContract.RecipeEntry.COLUMN_MODIFED
        };

        String sortOrder = CookbookContract.RecipeEntry.COLUMN_MODIFED + " DESC";

        try {
            Cursor cursor = db.query(CookbookContract.RecipeEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
            if (cursor.moveToFirst()) {
                String dateString = cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_MODIFED));
                return dateFormat.parse(dateString);
            }
        } catch (ParseException e) {
            return null;
        }

        return null;
    }

    public void insertAll(final List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            insert(recipe);
        }
        log.trace("Finished inserting recipes");
    }

    public void insert(final Recipe recipe) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            // Insert the recipe.
            ContentValues recipeValues = new ContentValues();
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_ID, recipe.getId());
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_TITLE, recipe.getTitle());
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_TYPE, recipe.getType());
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_SEASON, recipe.getSeason());
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_FAVOURITE, false);
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_TYPE, recipe.getType());
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_CREATED, dateFormat.format(recipe.getAddedDate()));
            recipeValues.put(CookbookContract.RecipeEntry.COLUMN_MODIFED, dateFormat.format(recipe.getUpdatedDate()));
            db.insert(CookbookContract.RecipeEntry.TABLE_NAME, "null", recipeValues);

            // Insert the search items.
            // TODO

            // Insert the categories.
            // TODO

            // Insert the ingredients.
            // TODO

            // Insert the directions.
            // TODO

            // Insert the notes.
            // TODO

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
