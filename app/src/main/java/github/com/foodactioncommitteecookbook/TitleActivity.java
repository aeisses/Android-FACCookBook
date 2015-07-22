package github.com.foodactioncommitteecookbook;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import de.greenrobot.event.EventBus;
import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.network.RecipeRequest;
import github.com.foodactioncommitteecookbook.network.RequestHelper;

/**
 * The TitleActivity shows an initial splash screen while it does the data sync from the server.
 * When the data sync is complete it moves on to the MainActivity.
 */
@EActivity(R.layout.activity_title)
public class TitleActivity extends Activity {

  private static final Logger log = LoggerFactory.getLogger(TitleActivity_.class);

  @AfterViews
  public void init() {
    EventBus.getDefault().registerSticky(this);

    if (isFinishing()) {
      return;
    }

    // Retrieve the last modified date for any recipe.
    Date lastModified = CookbookDb.instance().getLastModifiedDate();

    log.trace("Last modified date is {}", lastModified);

    // If the DB is empty then fetch the entire contents from the server. Otherwise, fetch
    // a delta of changes.
    if (lastModified == null) {
      fetchAllRecipes();
    } else {
      fetchRecipesSince(lastModified);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  private void fetchAllRecipes() {
    log.trace("Fetching all recipes");
    RecipeRequest request = new RecipeRequest();
    RequestHelper.instance().send(request);
  }

  private void fetchRecipesSince(final Date date) {
    log.trace("Fetching new recipes");
    // TODO fetch updated recipes.
    gotoMain();
  }

  @SuppressWarnings("unused")
  public void onEvent(RecipeRequest.CompleteEvent event) {
    CookbookDb.instance().insertAll(event.getRecipes());

    log.debug("Imported recipes. Launching main activity");
    gotoMain();
  }

  @SuppressWarnings("unused")
  public void onEvent(RecipeRequest.ErrorEvent event) {
    //noinspection ThrowableResultOfMethodCallIgnored
    log.error(event.getVolleyError().toString());
    Toast.makeText(this, "Unable to fetch recipes", Toast.LENGTH_LONG).show();
    finish();
  }

  private void gotoMain() {
    Intent intent = new Intent(this, MainActivity_.class);
    startActivity(intent);
    finish();
  }

}
