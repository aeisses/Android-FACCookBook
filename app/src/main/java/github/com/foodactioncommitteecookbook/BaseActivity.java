package github.com.foodactioncommitteecookbook;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Abstract base class containing logic common between activities.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Add SearchWidget.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.options_menu_search).getActionView();

        ComponentName componentName = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu_favourites:
                openFavourites();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void openFavourites() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
