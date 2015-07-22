package github.com.foodactioncommitteecookbook;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.com.foodactioncommitteecookbook.db.CookbookContract;
import github.com.foodactioncommitteecookbook.db.CookbookDb;

/**
 * Allows searching for recipes
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    private static Logger log = LoggerFactory.getLogger(SearchActivity_.class);

    @ViewById
    ListView searchList;

    @AfterViews
    protected void init() {
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            log.trace("Searching for {}", query);

            Cursor cursor = CookbookDb.instance().searchForRecipes(query);

            String[] columns = {
                    CookbookContract.RecipeEntry.COLUMN_TITLE
            };

            int[] toViews = {
                    R.id.recipe_title
            };

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_recipe, cursor, columns, toViews, 0);
            searchList.setAdapter(adapter);
        }
    }
}
