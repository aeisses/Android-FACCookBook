package github.com.foodactioncommitteecookbook.search;

import java.util.List;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public interface SearchView {
  void showRecipeList(List<Recipe> recipes);
}
