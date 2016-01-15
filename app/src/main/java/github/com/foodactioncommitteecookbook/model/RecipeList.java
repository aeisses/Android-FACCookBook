package github.com.foodactioncommitteecookbook.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class RecipeList {
  @SerializedName("recipes") private List<Recipe> recipes;

  public List<Recipe> getRecipes() {
    return recipes;
  }
}
