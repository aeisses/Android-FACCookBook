package github.com.foodactioncommitteecookbook.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.RecipeAdapter;
import github.com.foodactioncommitteecookbook.model.Recipe;
import timber.log.Timber;

/**
 * Fragment for searching for recipes
 */
public class SearchFragment extends Fragment implements SearchView, android.support.v7.widget.SearchView.OnQueryTextListener {

  SearchPresenter presenter;
  RecipeAdapter adapter;

  @Bind(R.id.search_input) android.support.v7.widget.SearchView searchView;
  @Bind(R.id.search_results) RecyclerView recipeList;

  //------------------------------------------------------------------------------------------------
  // Fragment Overrides
  //------------------------------------------------------------------------------------------------

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, container, false);
    ButterKnife.bind(this, view);

    Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
    toolbar.setTitle(R.string.search_title);

    searchView.setOnQueryTextListener(this);

    presenter = new SearchPresenterImpl(this);

    recipeList.setLayoutManager(new GridLayoutManager(getContext(), 2));
    adapter = new RecipeAdapter(new ArrayList<>(), presenter);
    recipeList.setAdapter(adapter);

    return view;
  }

  //------------------------------------------------------------------------------------------------
  // SearchView API
  //------------------------------------------------------------------------------------------------

  @Override public void showRecipeList(List<Recipe> recipes) {
    Timber.d("Found %d matches", recipes.size());
    adapter.setRecipes(recipes);
  }

  //------------------------------------------------------------------------------------------------
  // OnQueryTextListener API
  //------------------------------------------------------------------------------------------------

  @Override public boolean onQueryTextSubmit(String query) {
    Timber.d("Searching for %s", query);
    presenter.setSearchTerm(query);
    return true;
  }

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }
}
