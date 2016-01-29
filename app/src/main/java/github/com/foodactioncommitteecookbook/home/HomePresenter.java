package github.com.foodactioncommitteecookbook.home;

import github.com.foodactioncommitteecookbook.RecipeAdapter;

/**
 *
 */
public interface HomePresenter extends RecipeAdapter.SelectionListener {

  void onCreate();

  void onDestroy();
}
