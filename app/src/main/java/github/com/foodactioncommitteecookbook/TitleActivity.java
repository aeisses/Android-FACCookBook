package github.com.foodactioncommitteecookbook;

import android.app.Activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Date;

import github.com.foodactioncommitteecookbook.db.CookbookDb;

/**
 * The TitleActivity shows an initial splash screen while it does the data sync from the server.
 * When the data sync is complete it moves on to the MainActivity.
 */
@EActivity(R.layout.activity_title)
public class TitleActivity extends Activity {

    @AfterViews
    public void init() {
        // Retrieve the last modified date for any recipe.
        Date lastModified = CookbookDb.instance().getLastModifiedDate();

        // If the DB is empty then fetch the entire contents from the server. Otherwise, fetch
        // a delta of changes.
        if (lastModified == null) {
            fetchAllRecipes();
        } else {
            fetchRecipesSince(lastModified);
        }
    }

    private void fetchAllRecipes() {

    }

    private void fetchRecipesSince(final Date date) {

    }

}
