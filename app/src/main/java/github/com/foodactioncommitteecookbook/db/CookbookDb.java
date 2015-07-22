package github.com.foodactioncommitteecookbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import github.com.foodactioncommitteecookbook.model.Location;
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
        db.execSQL(CookbookContract.LocationEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over.
        db.execSQL(CookbookContract.SQL_DELETE_ENTRIES);
        db.execSQL(CookbookContract.LocationEntry.DROP_TABLE);
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

    public Cursor searchForRecipes(final String query) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = CookbookContract.RecipeEntry.COLUMN_TITLE + " LIKE ?";
        String[] args = {"%" + query + "%"};

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CookbookContract.RecipeEntry.TABLE_NAME);

        return builder.query(db, null, selection, args, null, null, null);
    }

    public List<Location> getLocations() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CookbookContract.LocationEntry.TABLE_NAME, null, null, null, null, null, null);

        List<Location> locations = new LinkedList<>();
        while (cursor.moveToNext()) {
            locations.add(new Location(
                    cursor.getInt(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_TITLE)),
                    cursor.getFloat(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_LATITUDE)),
                    cursor.getFloat(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_STORY))
            ));
        }
        cursor.close();
        return locations;
    }

    public void insertLocations(final List<Location> locations) {
        SQLiteDatabase db = getWritableDatabase();
        for (Location location : locations) {
            ContentValues values = new ContentValues();
            values.put(CookbookContract.LocationEntry.COLUMN_ID, location.getLocationId());
            values.put(CookbookContract.LocationEntry.COLUMN_TITLE, location.getTitle());
            values.put(CookbookContract.LocationEntry.COLUMN_LATITUDE, location.getLatitude());
            values.put(CookbookContract.LocationEntry.COLUMN_LONGITUDE, location.getLongitude());
            values.put(CookbookContract.LocationEntry.COLUMN_ADDRESS, location.getAddress());
            values.put(CookbookContract.LocationEntry.COLUMN_EMAIL, location.getEmail());
            values.put(CookbookContract.LocationEntry.COLUMN_PHONE, location.getPhone());
            values.put(CookbookContract.LocationEntry.COLUMN_STORY, location.getStory());
            db.insert(CookbookContract.LocationEntry.TABLE_NAME, null, values);
        }
    }
}
