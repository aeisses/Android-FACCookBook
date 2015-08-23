package github.com.foodactioncommitteecookbook;

import android.content.SharedPreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The main screen in the application.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
  private static final Logger log = LoggerFactory.getLogger(MainActivity_.class);

  final static String FEATURED_RECIPE_ID = "featured_recipe_id";

  @Extra(FEATURED_RECIPE_ID)
  int featuredRecipeId;

  @ViewById(R.id.main_activity_featured_recipe)
  RecipeView featuredRecipe;

  @ViewById(R.id.main_activity_recipe_grid)
  RecipeGrid recipeGrid;

  @AfterViews
  void updateFeaturedRecipe () {
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
