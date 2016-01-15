package github.com.foodactioncommitteecookbook.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.ImageHelper;


/**
 * The main screen in the application.
 */
public class MainActivity extends BaseActivity implements MainView {

  public final static String FEATURED_RECIPE_ID = "featured_recipe_id";

  private MainPresenter presenter;

  @Bind(R.id.main_activity_featured_recipe) ImageView mainRecipeView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    List<Integer> featuredIds = intent.getIntegerArrayListExtra(FEATURED_RECIPE_ID);

    presenter = new MainPresenterImpl(this, featuredIds);
    presenter.onCreate();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void setRecipes(List<Recipe> recipes) {
    if (recipes.size() == 0) {
      return;
    }

    setMainRecipe(recipes.remove(0));

    // TODO : Display others
  }

  @Override public Context getContext() {
    return this;
  }

  private void setMainRecipe(Recipe recipe) {
    ImageHelper.loadRecipeImage(this, recipe, mainRecipeView);
    mainRecipeView.setOnClickListener(view -> presenter.onRecipeSelected(recipe));
  }
}
