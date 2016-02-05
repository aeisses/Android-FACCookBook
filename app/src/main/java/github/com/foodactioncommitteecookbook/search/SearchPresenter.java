package github.com.foodactioncommitteecookbook.search;

import github.com.foodactioncommitteecookbook.RecipeAdapter;

/**
 *
 */
public interface SearchPresenter extends RecipeAdapter.SelectionListener {
  void setSearchTerm(String searchTerm);
}
