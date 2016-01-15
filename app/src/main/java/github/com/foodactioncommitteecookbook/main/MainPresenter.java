package github.com.foodactioncommitteecookbook.main;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public interface MainPresenter {

  void onCreate();

  void onDestroy();

  void onRecipeSelected(Recipe recipe);
}
