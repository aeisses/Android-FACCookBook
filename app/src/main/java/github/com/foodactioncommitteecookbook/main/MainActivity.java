package github.com.foodactioncommitteecookbook.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.RecipeAdapter;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.ImageHelper;


/**
 * The main screen in the application.
 */
public class MainActivity extends BaseActivity implements MainView {

  private static final int NUM_COLUMNS = 2;
  public static final String FEATURED_RECIPE_ID = "featured_recipe_id";

  private MainPresenter presenter;

  @Bind(R.id.main_activity_main_recipe) CardView mainRecipeView;
  @Bind(R.id.main_activity_featured_recipes) RecyclerView recipeList;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    Intent intent = getIntent();
    List<Integer> featuredIds = intent.getIntegerArrayListExtra(FEATURED_RECIPE_ID);

    recipeList.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
    recipeList.addItemDecoration(new ItemDecoration(getResources().getDimensionPixelSize(R.dimen.recipe_card_spacing)));

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
    setFeaturedRecipes(recipes);
  }

  @Override public Context getContext() {
    return this;
  }

  private void setMainRecipe(Recipe recipe) {
    ImageView imageView = (ImageView) mainRecipeView.findViewById(R.id.recipe_image);
    TextView textView = (TextView) mainRecipeView.findViewById(R.id.recipe_title);

    ImageHelper.loadRecipeImage(this, recipe, imageView);
    textView.setText(recipe.getTitle());
    mainRecipeView.setOnClickListener(view -> presenter.onRecipeSelected(recipe));
  }

  private void setFeaturedRecipes(List<Recipe> recipes) {
    RecipeAdapter adapter = new RecipeAdapter(recipes, presenter);
    recipeList.setAdapter(adapter);
  }

  private static class ItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    ItemDecoration(int spacing) {
      this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      int position = parent.getChildAdapterPosition(view);
      int column = position % NUM_COLUMNS;
      outRect.left = column * spacing / NUM_COLUMNS;
      outRect.right = spacing - (column + 1) * spacing / NUM_COLUMNS;
      outRect.top = spacing;
    }
  }
}
