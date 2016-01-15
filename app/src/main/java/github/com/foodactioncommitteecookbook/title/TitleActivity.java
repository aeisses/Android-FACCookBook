package github.com.foodactioncommitteecookbook.title;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.Date;

import de.greenrobot.event.EventBus;
import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.main.MainActivity;
import github.com.foodactioncommitteecookbook.network.FeaturedRecipeRequest;
import github.com.foodactioncommitteecookbook.network.RecipeRequest;
import github.com.foodactioncommitteecookbook.network.RequestHelper;
import timber.log.Timber;

/**
 * The TitleActivity shows an initial splash screen while it does the data sync from the server.
 * When the data sync is complete it moves on to the MainActivity.
 */
public class TitleActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().registerSticky(this);

    if (isFinishing()) {
      return;
    }

    // Retrieve the last modified date for any recipe.
    Date lastModified = CookbookDb.instance().getLastModifiedDate();

    // If the DB is empty then fetch the entire contents from the server. Otherwise, fetch
    // a delta of changes.
    if (lastModified == null) {
      fetchAllRecipes();
    } else {
      Timber.v("Last modified date is %s", lastModified.toString());
      fetchRecipesSince(lastModified);
    }

    fetchFeaturedRecipe();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  private void fetchAllRecipes() {
    Timber.v("Fetching all recipes");
    RecipeRequest request = new RecipeRequest();
    RequestHelper.instance().send(request);
  }

  private void fetchRecipesSince(final Date date) {
    Timber.v("Fetching new recipes");
    // TODO fetch updated recipes.
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        gotoMain();
      }
    }, 2000);
  }

  private void fetchFeaturedRecipe() {
    Timber.v("Fetching featured recipe");
    FeaturedRecipeRequest request = new FeaturedRecipeRequest();
    RequestHelper.instance().send(request);
  }


  @SuppressWarnings("unused")
  public void onEvent(RecipeRequest.CompleteEvent event) {
    CookbookDb.instance().insertAll(event.getRecipes());

    Timber.d("Imported recipes. Launching main activity");
    gotoMain();
  }

  @SuppressWarnings("unused")
  public void onEvent(FeaturedRecipeRequest.CompleteEvent event) {
    Timber.v("Got featured recipe");
    gotoMain();
  }


  @SuppressWarnings("unused")
  public void onEvent(RecipeRequest.ErrorEvent event) {
    //noinspection ThrowableResultOfMethodCallIgnored
    Timber.e(event.getVolleyError().toString());
    Toast.makeText(this, "Unable to fetch recipes", Toast.LENGTH_LONG).show();
    gotoMain();
  }

  @SuppressWarnings("unused")
  public void onEvent(FeaturedRecipeRequest.ErrorEvent event) {
    //noinspection ThrowableResultOfMethodCallIgnored
    Timber.e(event.getVolleyError().toString());
    Toast.makeText(this, "Unable to fetch featured recipe", Toast.LENGTH_LONG).show();
    gotoMain();
  }

  private void gotoMain() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

}
