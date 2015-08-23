package github.com.foodactioncommitteecookbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class RecipeGrid extends GridView {

  private static final Logger log = LoggerFactory.getLogger(RecipeGrid.class);

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({RECIPES_RANDOM, RECIPES_NEWEST})
  public @interface RecipeSet {
  }

  public static final int RECIPES_RANDOM = 0;
  public static final int RECIPES_NEWEST = 1;


  @RecipeSet
  private int recipeSet = RECIPES_RANDOM;
  private RecipeGridViewAdapter recipeGridViewAdapter;

  public RecipeGrid (Context context) {
    this(context, null);
  }

  public RecipeGrid (final Context context, AttributeSet attrs) {
    super(context, attrs);

    if (attrs != null) {
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RecipesGrid);

      try {
        //noinspection ResourceType
        recipeSet = a.getInt(R.styleable.RecipesGrid_sorted_by, RECIPES_RANDOM);

      } catch (Exception e) {
        log.warn("Failed to load custom attributes: {}", e);
      }

      a.recycle();
    }

    recipeGridViewAdapter = new RecipeGridViewAdapter(context);
    setAdapter(recipeGridViewAdapter);

    setOnItemClickListener(new OnItemClickListener() {
      public void onItemClick (AdapterView<?> parent, View v, int position, long id) {
        RecipeItem item = (RecipeItem) parent.getItemAtPosition(position);

        //Create intent
        Intent intent = new Intent(context, RecipeActivity.class);
/*
        intent.putExtra("title", item.getTitle());
        intent.putExtra("image", item.getImage());
*/

        //Start details activity
        context.startActivity(intent);
      }
    });

  }
}
