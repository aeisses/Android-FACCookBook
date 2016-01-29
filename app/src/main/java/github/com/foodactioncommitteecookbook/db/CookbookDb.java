package github.com.foodactioncommitteecookbook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import github.com.foodactioncommitteecookbook.model.Location;
import github.com.foodactioncommitteecookbook.model.Recipe;
import timber.log.Timber;

/**
 * SQLiteOpenHelper implementation for interacting with the recipe cache.
 */
public class CookbookDb extends SQLiteOpenHelper {

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
    db.execSQL(CookbookContract.RecipeEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.IngredientEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.DirectionEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.CategoryEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.SearchItemEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.NoteEntry.CREATE_TABLE);
    db.execSQL(CookbookContract.LocationEntry.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over.
    db.execSQL(CookbookContract.RecipeEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.IngredientEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.DirectionEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.CategoryEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.SearchItemEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.NoteEntry.DELETE_TABLE);
    db.execSQL(CookbookContract.LocationEntry.DELETE_TABLE);
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
        CookbookContract.RecipeEntry.COLUMN_MODIFIED
    };

    String sortOrder = CookbookContract.RecipeEntry.COLUMN_MODIFIED + " DESC";

    try {
      Cursor cursor = db.query(CookbookContract.RecipeEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);
      if (cursor.moveToFirst()) {
        String dateString = cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_MODIFIED));
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
    Timber.v("Finished inserting recipes");
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
      recipeValues.put(CookbookContract.RecipeEntry.COLUMN_MODIFIED, dateFormat.format(recipe.getUpdatedDate()));
      db.insertWithOnConflict(CookbookContract.RecipeEntry.TABLE_NAME, null, recipeValues, SQLiteDatabase.CONFLICT_REPLACE);

      // Insert the search items.
      for (String searchItem : recipe.getSearchItems()) {
        ContentValues searchValues = new ContentValues();
        searchValues.put(CookbookContract.SearchItemEntry.COLUMN_RECIPE_ID, recipe.getId());
        searchValues.put(CookbookContract.SearchItemEntry.COLUMN_SEARCH_ITEM, searchItem);
        db.insertWithOnConflict(CookbookContract.SearchItemEntry.TABLE_NAME, null, searchValues, SQLiteDatabase.CONFLICT_REPLACE);
      }

      // Insert the categories.
      for (String category : recipe.getCategories()) {
        ContentValues categoryValues = new ContentValues();
        categoryValues.put(CookbookContract.CategoryEntry.COLUMN_RECIPE_ID, recipe.getId());
        categoryValues.put(CookbookContract.CategoryEntry.COLUMN_CATEGORY, category);
        db.insertWithOnConflict(CookbookContract.CategoryEntry.TABLE_NAME, null, categoryValues, SQLiteDatabase.CONFLICT_REPLACE);
      }

      // Insert the ingredients.
      for (Recipe.Ingredient ingredient : recipe.getIngredients()) {
        ContentValues ingredientValues = new ContentValues();
        ingredientValues.put(CookbookContract.IngredientEntry.COLUMN_RECIPE_ID, recipe.getId());
        ingredientValues.put(CookbookContract.IngredientEntry.COLUMN_AMOUNT, ingredient.getAmount());
        ingredientValues.put(CookbookContract.IngredientEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
        db.insertWithOnConflict(CookbookContract.IngredientEntry.TABLE_NAME, null, ingredientValues, SQLiteDatabase.CONFLICT_REPLACE);
      }

      // Insert the directions.
      for (Recipe.Direction direction : recipe.getDirections()) {
        ContentValues directionValues = new ContentValues();
        directionValues.put(CookbookContract.DirectionEntry.COLUMN_RECIPE_ID, recipe.getId());
        directionValues.put(CookbookContract.DirectionEntry.COLUMN_DIRECTION, direction.getDirection());
        db.insertWithOnConflict(CookbookContract.DirectionEntry.TABLE_NAME, null, directionValues, SQLiteDatabase.CONFLICT_REPLACE);
      }

      // Insert the notes.
      for (Recipe.Note note : recipe.getNotes()) {
        ContentValues noteValues = new ContentValues();
        noteValues.put(CookbookContract.NoteEntry.COLUMN_RECIPE_ID, recipe.getId());
        noteValues.put(CookbookContract.NoteEntry.COLUMN_NOTE, note.getNote());
        db.insertWithOnConflict(CookbookContract.NoteEntry.TABLE_NAME, null, noteValues, SQLiteDatabase.CONFLICT_REPLACE);
      }

      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  public Recipe getRecipe(int recipeId) {
    SQLiteDatabase db = getWritableDatabase();

    String[] args = new String[]{String.valueOf(recipeId)};

    Cursor cursor = null;
    Recipe recipe;

    try {
      String query = "SELECT * FROM " + CookbookContract.RecipeEntry.TABLE_NAME +
          " WHERE " + CookbookContract.RecipeEntry.COLUMN_ID + " = ?";

      cursor = db.rawQuery(query, args);

      if (cursor.moveToFirst()) {
        recipe = new Recipe();
        recipe.setId(cursor.getInt(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_ID)));
        recipe.setTitle(cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_TITLE)));
        recipe.setType(cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_TYPE)));
        recipe.setSeason(cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_SEASON)));
        recipe.setAddedDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_CREATED))));
        recipe.setUpdatedDate(dateFormat.parse(cursor.getString(cursor.getColumnIndex(CookbookContract.RecipeEntry.COLUMN_MODIFIED))));
      } else {
        return null;
      }

      cursor.close();

      query = "SELECT * FROM " + CookbookContract.IngredientEntry.TABLE_NAME +
          " WHERE " + CookbookContract.IngredientEntry.COLUMN_RECIPE_ID + " = ?";
      cursor = db.rawQuery(query, args);

      while (cursor.moveToNext()) {
        Recipe.Ingredient ingredient = new Recipe.Ingredient();
        ingredient.setAmount(cursor.getString(cursor.getColumnIndex(CookbookContract.IngredientEntry.COLUMN_AMOUNT)));
        ingredient.setIngredient(cursor.getString(cursor.getColumnIndex(CookbookContract.IngredientEntry.COLUMN_INGREDIENT)));
        recipe.addIngredient(ingredient);
      }

      cursor.close();
      query = "SELECT * FROM " + CookbookContract.DirectionEntry.TABLE_NAME +
          " WHERE " + CookbookContract.DirectionEntry.COLUMN_RECIPE_ID + " = ?";
      cursor = db.rawQuery(query, args);

      while (cursor.moveToNext()) {
        Recipe.Direction direction = new Recipe.Direction();
        direction.setDirection(cursor.getString(cursor.getColumnIndex(CookbookContract.DirectionEntry.COLUMN_DIRECTION)));
        recipe.addDirection(direction);
      }

      cursor.close();
      query = "SELECT * FROM " + CookbookContract.CategoryEntry.TABLE_NAME +
          " WHERE " + CookbookContract.CategoryEntry.COLUMN_RECIPE_ID + " = ?";
      cursor = db.rawQuery(query, args);

      while (cursor.moveToNext()) {
        recipe.addCategory(cursor.getString(cursor.getColumnIndex(CookbookContract.CategoryEntry.COLUMN_CATEGORY)));
      }

      cursor.close();
      query = "SELECT * FROM " + CookbookContract.NoteEntry.TABLE_NAME +
          " WHERE " + CookbookContract.NoteEntry.COLUMN_RECIPE_ID + " = ?";
      cursor = db.rawQuery(query, args);

      while (cursor.moveToNext()) {
        Recipe.Note note = new Recipe.Note();
        note.setNote(cursor.getString(cursor.getColumnIndex(CookbookContract.NoteEntry.COLUMN_NOTE)));
        recipe.addNote(note);
      }

      cursor.close();
      query = "SELECT * FROM " + CookbookContract.SearchItemEntry.TABLE_NAME +
          " WHERE " + CookbookContract.SearchItemEntry.COLUMN_RECIPE_ID + " = ?";
      cursor = db.rawQuery(query, args);

      while (cursor.moveToNext()) {
        recipe.addSearchItems(cursor.getString(cursor.getColumnIndex(CookbookContract.SearchItemEntry.COLUMN_SEARCH_ITEM)));
      }

      return recipe;
    } catch (Exception e) {
      Timber.e(e, "Unable to retrieve recipe %d", recipeId);
      return null;
    } finally {
      if (cursor != null) {
        cursor.close();
      }
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
          new Date(cursor.getLong(cursor.getColumnIndex(CookbookContract.LocationEntry.COLUMN_ADDEDDATE))),
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
      values.put(CookbookContract.LocationEntry.COLUMN_ADDEDDATE, location.getAddedDate().getTime());
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
