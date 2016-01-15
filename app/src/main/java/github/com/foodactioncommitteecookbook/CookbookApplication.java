package github.com.foodactioncommitteecookbook;

import android.app.Application;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.map.FindLocationTask;
import github.com.foodactioncommitteecookbook.network.RequestHelper;
import timber.log.Timber;

/**
 * Application override for creating singletons.
 */
public class CookbookApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    // Create our singleton DB helper instance.
    CookbookDb.create(getApplicationContext());

    // Create the single Volley helper.
    RequestHelper.create(getApplicationContext());

    // Search for the user's location
    new FindLocationTask().execute(this);
  }
}
