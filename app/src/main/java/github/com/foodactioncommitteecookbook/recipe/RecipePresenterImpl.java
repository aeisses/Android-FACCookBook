package github.com.foodactioncommitteecookbook.recipe;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public class RecipePresenterImpl implements RecipePresenter {

  private final RecipeView view;
  private final Recipe recipe;

  RecipePresenterImpl(RecipeView view, Recipe recipe) {
    this.view = view;
    this.recipe = recipe;
  }

  @Override public void onCreate() {
    String url = "https://dl.dropboxusercontent.com/u/10173737/Cookbook/Images/Medium/" + Integer.toString(recipe.getId()) + "-Standard-Tablet-Medium.png";

    view.setTitle(recipe.getTitle());
    view.setHeaderImage(url);
    view.setDirections(recipe.getDirections());
    view.setIngredients(recipe.getIngredients());

    if (recipe.getNotes().size() > 0) {
      view.setNotes(recipe.getNotes());
    } else {
      view.setNotesVisible(false);
    }
  }

  @Override public void onDestroy() {

  }
}
