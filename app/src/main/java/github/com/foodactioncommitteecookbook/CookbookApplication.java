package github.com.foodactioncommitteecookbook;

import android.app.Application;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.network.RequestHelper;

/**
 * Application override for creating singletons.
 */
public class CookbookApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Create our singleton DB helper instance.
        CookbookDb.create(getApplicationContext());

        // Create the single Volley helper.
        RequestHelper.create(getApplicationContext());
    }
}
