package github.com.foodactioncommitteecookbook.main;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.recipe.RecipeActivity;

/**
 *
 */
class MainPresenterImpl implements MainPresenter {

  MainView view;
  List<Integer> featuredIds = new ArrayList<>();

  MainPresenterImpl(MainView view, List<Integer> featuredIds) {
    this.view = view;
    this.featuredIds.addAll(featuredIds);
  }

  @Override public void onCreate() {
    List<Recipe> recipes = new ArrayList<>();
    for (Integer id : featuredIds) {
      Recipe recipe = CookbookDb.instance().getRecipe(id);
      if (recipe != null) {
        recipes.add(recipe);
      }
    }
    view.setRecipes(recipes);
  }

  @Override public void onDestroy() {

  }

  @Override public void onRecipeSelected(Recipe recipe) {
    Context context = view.getContext();
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(RecipeActivity.INTENT_RECIPE, recipe);
    context.startActivity(intent);
  }
}
