package github.com.foodactioncommitteecookbook.model;

import com.android.volley.VolleyError;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for Location data.
 */
public class FeaturedRecipe {

  @SerializedName("recipeId")
  private int recipeId;

  public FeaturedRecipe (int recipeId) {
    this.recipeId = recipeId;
  }

  public int getRecipeId () {
    return recipeId;
  }
}
