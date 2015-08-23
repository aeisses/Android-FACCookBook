package github.com.foodactioncommitteecookbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Contains a list of recipes that can be selected.
 */
@EActivity(R.layout.activity_list)
public class ListActivity extends BaseActivity {

    public enum Page {
        POPULAR(R.string.tab_popular),
        FAVOURITES(R.string.tab_favourites),
        SEASON(R.string.tab_season);

        private final int resourceId;

        Page(int resourceId) {
            this.resourceId = resourceId;
        }
    }

    @ViewById
    ViewPager listPager;

    @AfterViews
    public void init() {
        RecipeListPagerAdapter adapter = new RecipeListPagerAdapter(getSupportFragmentManager());
        listPager.setAdapter(adapter);
    }

    public static class RecipeListPagerAdapter extends FragmentPagerAdapter {
        public RecipeListPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return RecipeListFragment.newInstance();
        }

        @Override
        public int getCount() {
            return Page.values().length;
        }
    }

}
