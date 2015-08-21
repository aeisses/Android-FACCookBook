package github.com.foodactioncommitteecookbook;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class RecipeListFragment extends ListFragment {

  @Bind(android.R.id.list) ListView recipeList;

  static RecipeListFragment newInstance() {
    RecipeListFragment fragment = new RecipeListFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

}
