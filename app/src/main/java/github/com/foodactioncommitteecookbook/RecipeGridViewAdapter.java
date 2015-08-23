package github.com.foodactioncommitteecookbook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class RecipeGridViewAdapter extends ArrayAdapter<RecipeItem> {
  private Context context;
  private ArrayList<RecipeItem> data = new ArrayList<RecipeItem>();


  public RecipeGridViewAdapter (Context context) {
    super(context, R.layout.recipe_item_layout);

    this.context = context;
  }

  @Override
  public View getView (int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.recipe_item_layout, parent);
    }

    RecipeItem item = data.get(position);
    return convertView;
  }
}
