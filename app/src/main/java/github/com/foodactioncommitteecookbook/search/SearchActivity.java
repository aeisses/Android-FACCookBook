package github.com.foodactioncommitteecookbook.search;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.db.CookbookContract;
import github.com.foodactioncommitteecookbook.db.CookbookDb;
import timber.log.Timber;

/**
 * Allows searching for recipes
 */
public class SearchActivity extends BaseActivity {

  @Bind(R.id.searchList) ListView searchList;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    handleIntent(intent);
  }

  private void handleIntent(final Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      Timber.v("Searching for {}", query);

      Cursor cursor = CookbookDb.instance().searchForRecipes(query);

      String[] columns = {
          CookbookContract.RecipeEntry.COLUMN_TITLE
      };

      int[] toViews = {
          R.id.main_activity_recipe_grid
      };

      SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.recipe_item_layout, cursor, columns, toViews, 0);
      searchList.setAdapter(adapter);
    }
  }
}
