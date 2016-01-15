package github.com.foodactioncommitteecookbook.network;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 *
 */
public class ImageHelper {

  public static void loadRecipeImage(Context context, Recipe recipe, ImageView view) {
    String url = "https://dl.dropboxusercontent.com/u/10173737/Cookbook/Images/Medium/" + Integer.toString(recipe.getId()) + "-Standard-Tablet-Medium.png";
    Picasso.with(context)
        .load(url)
        .placeholder(R.drawable.default_recipe_image)
        .error(R.drawable.error_recipe_image)
        .into(view);
  }

}
