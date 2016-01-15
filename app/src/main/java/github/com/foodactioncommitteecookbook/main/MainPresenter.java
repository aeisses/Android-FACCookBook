package github.com.foodactioncommitteecookbook.main;

import github.com.foodactioncommitteecookbook.RecipeAdapter;

/**
 *
 */
public interface MainPresenter extends RecipeAdapter.SelectionListener {

  void onCreate();

  void onDestroy();
}
