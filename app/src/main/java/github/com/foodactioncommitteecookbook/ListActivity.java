package github.com.foodactioncommitteecookbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Contains a list of recipes that can be selected.
 */
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

    @Bind(R.id.listPager)
    ViewPager listPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

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
