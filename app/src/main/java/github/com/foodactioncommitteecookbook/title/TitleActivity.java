package github.com.foodactioncommitteecookbook.title;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import github.com.foodactioncommitteecookbook.CookbookApplication;
import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.main.MainActivity;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.model.RecipeList;
import github.com.foodactioncommitteecookbook.network.CookbookService;
import retrofit2.Call;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * The TitleActivity shows an initial splash screen while it does the data sync from the server.
 * When the data sync is complete it moves on to the MainActivity.
 */
public class TitleActivity extends Activity {

  Observable<List<Recipe>> recipes;
  Call<List<Integer>> featured;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (isFinishing()) {
      return;
    }

    // Retrieve the last modified date for any recipe.
    Date lastModified = CookbookDb.instance().getLastModifiedDate();

    // If the DB is empty then fetch the entire contents from the server. Otherwise, fetch
    // a delta of changes.
    Observable<RecipeList> recipes = fetchRecipes(lastModified);
    Observable<List<Integer>> featured = fetchFeaturedRecipes();

    Observable.zip(recipes, featured, (recipesList, integers) -> {
      CookbookDb.instance().insertAll(recipesList.getRecipes());
      return integers;
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(this::gotoMain);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

  }

  private Observable<RecipeList> fetchRecipes(final Date date) {
    if (date == null) {
      Timber.v("Fetching all recipes");
      return getCookbookService().recipes();
    } else {
      Timber.v("Fetching new recipes from %s", date.toString());
      return getCookbookService().recipes(date.toString());
    }
  }

  private Observable<List<Integer>> fetchFeaturedRecipes() {
    Timber.v("Fetching featured recipe");
    return getCookbookService().featured();
  }

  private void gotoMain(List<Integer> featuredRecipes) {
    ArrayList<Integer> arrayList = new ArrayList<>(featuredRecipes.size());
    arrayList.addAll(featuredRecipes);

    Intent intent = new Intent(this, MainActivity.class);
    intent.putIntegerArrayListExtra(MainActivity.FEATURED_RECIPE_ID, arrayList);
    startActivity(intent);
    finish();
  }

  private CookbookService getCookbookService() {
    CookbookApplication application = (CookbookApplication) getApplicationContext();
    return application.getService();
  }
}
