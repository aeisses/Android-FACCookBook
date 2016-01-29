package github.com.foodactioncommitteecookbook.search;

import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public class SearchPresenterImpl implements SearchPresenter {
  private List<Recipe> recipes = new LinkedList<>();

  @Override public void setSearchTerm(String searchTerm) {
    recipes.clear();
    Cursor cursor = CookbookDb.instance().searchForRecipes(searchTerm);

  }
}
