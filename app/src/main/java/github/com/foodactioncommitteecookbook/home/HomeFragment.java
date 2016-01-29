package github.com.foodactioncommitteecookbook.home;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.CookbookApplication;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.RecipeAdapter;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.ImageHelper;

/**
 * Fragment for the home screen.
 */
public class HomeFragment extends Fragment implements HomeView {

  private static final int NUM_COLUMNS = 2;

  private HomePresenter presenter;

  @Bind(R.id.home_main_recipe) CardView mainRecipeView;
  @Bind(R.id.home_featured_recipes) RecyclerView recipeList;

  //------------------------------------------------------------------------------------------------
  // Fragment Overrides
  //------------------------------------------------------------------------------------------------

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);

    recipeList.setLayoutManager(new GridLayoutManager(getContext(), NUM_COLUMNS));
    recipeList.addItemDecoration(new ItemDecoration(getResources().getDimensionPixelSize(R.dimen.recipe_card_spacing)));

    CookbookApplication application = (CookbookApplication) getContext().getApplicationContext();

    presenter = new HomePresenterImpl(this, application.getFeaturedIds());
    presenter.onCreate();

    return view;
  }

  //------------------------------------------------------------------------------------------------
  // HomeView API
  //------------------------------------------------------------------------------------------------

  @Override public void setRecipes(List<Recipe> recipes) {
    if (recipes.size() == 0) {
      return;
    }

    setMainRecipe(recipes.remove(0));
    setFeaturedRecipes(recipes);
  }

  //------------------------------------------------------------------------------------------------
  // Helpers
  //------------------------------------------------------------------------------------------------

  private void setMainRecipe(Recipe recipe) {
    ImageView imageView = (ImageView) mainRecipeView.findViewById(R.id.recipe_image);
    TextView textView = (TextView) mainRecipeView.findViewById(R.id.recipe_title);

    ImageHelper.loadRecipeImage(getContext(), recipe, imageView);
    textView.setText(recipe.getTitle());
    mainRecipeView.setOnClickListener(view -> presenter.onRecipeSelected(recipe));
  }

  private void setFeaturedRecipes(List<Recipe> recipes) {
    RecipeAdapter adapter = new RecipeAdapter(recipes, presenter);
    recipeList.setAdapter(adapter);
  }

  //------------------------------------------------------------------------------------------------
  // ItemDecoration for the RecyclerView
  //------------------------------------------------------------------------------------------------

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
