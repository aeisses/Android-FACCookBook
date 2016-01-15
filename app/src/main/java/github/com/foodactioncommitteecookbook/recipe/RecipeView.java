package github.com.foodactioncommitteecookbook.recipe;

import java.util.List;

import github.com.foodactioncommitteecookbook.AbstractView;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public interface RecipeView extends AbstractView {
  void setHeaderImage(String url);

  void setTitle(String title);

  void setIngredients(List<Recipe.Ingredient> ingredients);

  void setDirections(List<Recipe.Direction> directions);

  void setNotesVisible(boolean visible);

  void setNotes(List<Recipe.Note> notes);
}
