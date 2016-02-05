package github.com.foodactioncommitteecookbook.search;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public class SearchPresenterImpl implements SearchPresenter {

  SearchView view;

  SearchPresenterImpl(SearchView view) {
    this.view = view;
  }

  @Override public void setSearchTerm(String searchTerm) {
    view.showRecipeList(CookbookDb.instance().searchForRecipes(searchTerm));
  }

  @Override public void onRecipeSelected(Recipe recipe) {

  }
}
