package github.com.foodactioncommitteecookbook;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.RequestHelper;

/**
 * Displays a selected recipe
 */
@EActivity(R.layout.activity_recipe)
public class RecipeActivity extends BaseActivity {
  private static final Logger log = LoggerFactory.getLogger(RecipeActivity_.class);

  final static String INTENT_RECIPE = "recipe";

  @Extra(INTENT_RECIPE)
  Recipe recipe;

  @ViewById(R.id.recipe_title)
  TextView titleView;

  @ViewById(R.id.recipe_image)
  ImageView imageView;

  @AfterViews
  void updateRecipe () {
    try {
      Typeface timelessTypeface = Typeface.createFromAsset(getAssets(), "fonts/Timeless.ttf");
      titleView.setTypeface(timelessTypeface);

    } catch (Exception e) {
      log.warn("Unable to use custom font in recipe name.\n{}", e);
    }
    titleView.setTextSize(24);
    titleView.setText(recipe.getTitle());

    RequestHelper.imageLoader().get(
        "https://dl.dropboxusercontent.com/u/10173737/Cookbook/Images/Medium/" + Integer.toString(recipe.getId()) + "-Standard-Tablet-Medium.png",
        ImageLoader.getImageListener(imageView, R.drawable.default_recipe_image, R.drawable.error_recipe_image));
  }
}
