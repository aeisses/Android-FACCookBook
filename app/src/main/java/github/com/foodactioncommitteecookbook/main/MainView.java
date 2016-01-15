package github.com.foodactioncommitteecookbook.main;

import java.util.List;

import github.com.foodactioncommitteecookbook.AbstractView;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
interface MainView extends AbstractView {

  void setRecipes(List<Recipe> recipes);
}
