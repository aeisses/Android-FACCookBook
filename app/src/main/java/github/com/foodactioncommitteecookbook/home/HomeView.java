package github.com.foodactioncommitteecookbook.home;

import java.util.List;

import github.com.foodactioncommitteecookbook.AbstractView;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
interface HomeView extends AbstractView {

  void setRecipes(List<Recipe> recipes);
}
