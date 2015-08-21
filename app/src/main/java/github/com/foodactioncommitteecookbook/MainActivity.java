package github.com.foodactioncommitteecookbook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * The main screen in the application.
 */
public class MainActivity extends BaseActivity {

  final static String FEATURED_RECIPE_ID = "featured_recipe_id";

  int featuredRecipeId;

  @Bind(R.id.main_activity_featured_recipe) RecipeView featuredRecipe;
  @Bind(R.id.main_activity_recipe_grid) RecipeGrid recipeGrid;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    featuredRecipeId = getIntent().getIntExtra(FEATURED_RECIPE_ID, 0);

    SharedPreferences prefs = getPreferences(MODE_PRIVATE);

    if (featuredRecipeId == 0) {
      featuredRecipeId = prefs.getInt(FEATURED_RECIPE_ID, 0);
    }

    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt(FEATURED_RECIPE_ID, featuredRecipeId);
    editor.commit();

    featuredRecipe.setRecipeId(featuredRecipeId);
  }
}
