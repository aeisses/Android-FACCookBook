package github.com.foodactioncommitteecookbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.ImageHelper;

/**
 * RecyclerView Adapter implementation that can be used to display a list of Recipe cards.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

  private List<Recipe> recipes;
  private final SelectionListener selectionListener;

  public RecipeAdapter(List<Recipe> recipes, SelectionListener listener) {
    this.recipes = recipes;
    this.selectionListener = listener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.recipe_card, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Recipe recipe = recipes.get(position);
    Context context = holder.image.getContext();
    ImageHelper.loadRecipeImage(context, recipe, holder.image);
    holder.title.setText(recipe.getTitle());
    holder.itemView.setOnClickListener(view -> selectionListener.onRecipeSelected(recipe));
  }

  @Override public int getItemCount() {
    return recipes.size();
  }

  public void setRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
    notifyDataSetChanged();
  }

  public interface SelectionListener {
    void onRecipeSelected(Recipe recipe);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title;

    private ViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.recipe_image);
      title = (TextView) itemView.findViewById(R.id.recipe_title);
    }
  }
}
