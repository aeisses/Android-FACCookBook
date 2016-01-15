package github.com.foodactioncommitteecookbook;

import android.app.Application;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.map.FindLocationTask;
import github.com.foodactioncommitteecookbook.network.RecipeService;
import github.com.foodactioncommitteecookbook.network.ServiceFactory;
import timber.log.Timber;

/**
 * Application override for creating singletons.
 */
public class CookbookApplication extends Application {

  private RecipeService service;

  public RecipeService getService() {
    return service;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    // Create the HTTP service for querying the server.
    service = ServiceFactory.createService(RecipeService.class);

    // Create our singleton DB helper instance.
    CookbookDb.create(getApplicationContext());

    // Search for the user's location
    new FindLocationTask().execute(this);
  }
}
