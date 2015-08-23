package github.com.foodactioncommitteecookbook.db;

import android.provider.BaseColumns;

/**
 * Contract class for the database schema.
 */
public final class CookbookContract {

  private CookbookContract () {
  }

  // Update this version number if you change anything else in this file.
  public static final int DATABASE_VERSION = 4;

  public static final String DATABASE_NAME = "FACCookbook.db";

  public static final String SQL_CREATE_ENTRIES = RecipeEntry.CREATE_TABLE + ";"
      + IngredientEntry.CREATE_TABLE + ";"
      + LocationEntry.CREATE_TABLE;

  private static final String DELETE = "DROP TABLE IF EXISTS ";
  public static final String SQL_DELETE_ENTRIES = DELETE + CookbookContract.RecipeEntry.TABLE_NAME + ";"
      + DELETE + IngredientEntry.TABLE_NAME + ";"
      + DELETE + LocationEntry.TABLE_NAME;

  public static abstract class RecipeEntry implements BaseColumns {
    public static final String TABLE_NAME = "recipe";
    public static final String COLUMN_ID = "recipe_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_FAVOURITE = "is_favourite";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_CREATED = "created";
    public static final String COLUMN_MODIFIED = "last_modified";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        _ID + " INTEGER PRIMARY KEY, " +
        COLUMN_ID + " INTEGER, " +
        COLUMN_TITLE + " TEXT, " +
        COLUMN_TYPE + " TEXT, " +
        COLUMN_FAVOURITE + " INTEGER, " +
        COLUMN_SEASON + " TEXT, " +
        COLUMN_CREATED + " DATETIME, " +
        COLUMN_MODIFIED + " DATETIME " +
        ")";
  }

  public static abstract class IngredientEntry implements BaseColumns {
    public static final String TABLE_NAME = "ingredient";
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_INGREDIENT = "ingredient";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        _ID + " INTEGER PRIMARY KEY, " +
        "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + RecipeEntry.TABLE_NAME + "(" + RecipeEntry._ID + ")," +
        COLUMN_AMOUNT + " TEXT, " +
        COLUMN_INGREDIENT + " TEXT)";
    
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
  }

  public static abstract class LocationEntry implements BaseColumns {
    public static final String TABLE_NAME = "location";
    public static final String COLUMN_ID = "location_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ADDEDDATE = "addeddate";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_STORY = "story";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        _ID + " INTEGER PRIMARY KEY, " +
        COLUMN_ID + " INTEGER, " +
        COLUMN_TITLE + " TEXT, " +
        COLUMN_ADDEDDATE + " INTEGER, " +
        COLUMN_LATITUDE + " TEXT, " +
        COLUMN_LONGITUDE + " REAL, " +
        COLUMN_ADDRESS + " REAL, " +
        COLUMN_EMAIL + " TEXT, " +
        COLUMN_PHONE + " TEXT, " +
        COLUMN_STORY + " TEXT " +
        ")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
  }
}
