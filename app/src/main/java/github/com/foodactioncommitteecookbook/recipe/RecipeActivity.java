package github.com.foodactioncommitteecookbook.recipe;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.model.Recipe;
import timber.log.Timber;

/**
 * Displays a selected recipe
 */
public class RecipeActivity extends BaseActivity {

  public final static String INTENT_RECIPE = "recipe";

  Recipe recipe;
  @Bind(R.id.recipe_title) TextView titleView;
  @Bind(R.id.recipe_image) ImageView imageView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    recipe = (Recipe) intent.getSerializableExtra(INTENT_RECIPE);

    try {
      Typeface timelessTypeface = Typeface.createFromAsset(getAssets(), "fonts/Timeless.ttf");
      titleView.setTypeface(timelessTypeface);

    } catch (Exception e) {
      Timber.w("Unable to use custom font in recipe name.\n{}", e);
    }
    titleView.setTextSize(24);
    titleView.setText(recipe.getTitle());

    String url = "https://dl.dropboxusercontent.com/u/10173737/Cookbook/Images/Medium/" + Integer.toString(recipe.getId()) + "-Standard-Tablet-Medium.png";
    Picasso.with(this)
        .load(url)
        .placeholder(R.drawable.default_recipe_image)
        .error(R.drawable.error_recipe_image)
        .into(imageView);
  }
}
