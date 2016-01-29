package github.com.foodactioncommitteecookbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;


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
    item.setChecked(true);
    drawerLayout.closeDrawers();
    return true;
  }
}
