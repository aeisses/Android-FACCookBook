package github.com.foodactioncommitteecookbook;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.home.HomeFragment;
import github.com.foodactioncommitteecookbook.list.RecipeListFragment;
import github.com.foodactioncommitteecookbook.search.SearchFragment;


/**
 * The main screen in the application.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

  @Bind(R.id.main_toolbar) Toolbar toolbar;
  @Bind(R.id.main_drawer_layout) DrawerLayout drawerLayout;
  @Bind(R.id.nav_view) NavigationView navigationView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    ActionBar actionbar = getSupportActionBar();
    if (actionbar != null) {
      actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
      actionbar.setDisplayHomeAsUpEnabled(true);
    }

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.main_fragment_container, new HomeFragment());
    transaction.commit();

    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      drawerLayout.openDrawer(GravityCompat.START);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {
    drawerLayout.closeDrawers();

    if (!item.isChecked()) {
      item.setChecked(true);
      switchToScreen(item.getItemId());
    }

    return true;
  }

  private void switchToScreen(@IdRes int itemId) {
    Fragment newFragment;

    switch (itemId) {
      case R.id.nav_home:
      default:
        newFragment = new HomeFragment();
        break;
      case R.id.nav_list:
        newFragment = new RecipeListFragment();
        break;
      case R.id.nav_search:
        newFragment = new SearchFragment();
        break;
    }

    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.main_fragment_container, newFragment);
    transaction.commit();
  }
}
