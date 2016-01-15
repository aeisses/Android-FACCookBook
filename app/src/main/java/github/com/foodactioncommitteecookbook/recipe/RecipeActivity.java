package github.com.foodactioncommitteecookbook.recipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.model.Recipe;
import timber.log.Timber;

/**
 * Displays a selected recipe
 */
public class RecipeActivity extends BaseActivity implements RecipeView {

  public final static String INTENT_RECIPE = "recipe";

  Recipe recipe;
  RecipePresenter presenter;
  @Bind(R.id.recipe_title) TextView titleView;
  @Bind(R.id.recipe_image) ImageView imageView;
  //@Bind(R.id.recipe_ingredients) LinearLayout ingredientsView;
  //@Bind(R.id.recipe_directions) LinearLayout directionsView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    recipe = (Recipe) intent.getSerializableExtra(INTENT_RECIPE);

    presenter = new RecipePresenterImpl(this, recipe);
    presenter.onCreate();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void setHeaderImage(String url) {
    Picasso.with(this)
        .load(url)
        .placeholder(R.drawable.default_recipe_image)
        .error(R.drawable.error_recipe_image)
        .into(imageView);
  }

  @Override public void setTitle(String title) {
    try {
      Typeface timelessTypeface = Typeface.createFromAsset(getAssets(), "fonts/Timeless.ttf");
      titleView.setTypeface(timelessTypeface);

    } catch (Exception e) {
      Timber.w("Unable to use custom font in recipe name.\n{}", e);
    }
    titleView.setTextSize(24);
    titleView.setText(recipe.getTitle());
  }

  @Override public void setIngredients(List<Recipe.Ingredient> ingredients) {

  }

  @Override public void setDirections(List<Recipe.Direction> directions) {

  }

  @Override public void setNotesVisible(boolean visible) {

  }

  @Override public void setNotes(List<Recipe.Note> notes) {

  }

  @Override public Context getContext() {
    return this;
  }
}
