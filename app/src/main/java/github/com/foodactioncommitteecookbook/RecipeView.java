package github.com.foodactioncommitteecookbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Recipe;
import github.com.foodactioncommitteecookbook.network.RequestHelper;
import github.com.foodactioncommitteecookbook.recipe.RecipeActivity;
import timber.log.Timber;


public class RecipeView extends ImageView {

  private Recipe recipe;

  private Paint textPaint;
  private Paint rectPaint;
  private Rect recipeNameRect;
  private Point recipeNameOrigin;
  private int recipeNamePadding;

  public RecipeView(Context context) {
    this(context, null);
  }

  public RecipeView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RecipeView(final Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    if (attrs != null) {
      final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RecipeView, defStyle, 0);
      int id = a.getInt(R.styleable.RecipeView_recipeId, 0);
      if (id != 0) {
        setRecipeId(id);
      }
      a.recycle();
    }

    textPaint = new Paint();
    textPaint.setColor(getResources().getColor(R.color.recipe_name_text_color));
    textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    textPaint.setTextAlign(Paint.Align.LEFT);

    try {
      Typeface timelessTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Timeless.ttf");
      textPaint.setTypeface(timelessTypeface);

    } catch (Exception e) {
      Timber.w("Unable to use custom font in recipe name.\n{}", e);
    }

    rectPaint = new Paint();
    rectPaint.setColor(getResources().getColor(R.color.recipe_name_background));
    rectPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

    setImageResource(R.drawable.default_recipe_image);

    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent recipeIntent = new Intent(context, RecipeActivity.class);
        recipeIntent.putExtra(RecipeActivity.INTENT_RECIPE, recipe);

        context.startActivity(recipeIntent);
      }
    });
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    int paddingLeft = getPaddingLeft();
    int paddingTop = getPaddingTop();
    int paddingRight = getPaddingRight();
    int paddingBottom = getPaddingBottom();

    int contentWidth = getWidth() - paddingLeft - paddingRight;
    int contentHeight = getHeight() - paddingTop - paddingBottom;

    int rectHeight = contentHeight / 8;
    int rectXOffset = paddingLeft + contentWidth / 4;
    int rectYOffset = paddingTop + contentHeight * 3 / 4 - rectHeight;

    recipeNameRect = new Rect(
        rectXOffset,
        rectYOffset,
        contentWidth - paddingRight,
        rectYOffset + rectHeight
    );

    textPaint.setTextSize(h / 12.0f);

    recipeNamePadding = recipeNameRect.width() / 40;
    Paint.FontMetrics fm = textPaint.getFontMetrics();
    int textHeight = (int) (fm.bottom - fm.top + 0.5);

    recipeNameOrigin = new Point(
        recipeNameRect.left + recipeNamePadding,
        recipeNameRect.bottom - (recipeNameRect.height() - textHeight) / 2 - (int) fm.bottom
    );

    // right align the text if it is smaller than the available rectangle
    int availableTextWidth = recipeNameRect.width() - 2 * recipeNamePadding;
    int textWidth = (int) textPaint.measureText(getRecipeTitle()) + 1; // always round up
    if (textWidth < availableTextWidth) {
      recipeNameOrigin.x += availableTextWidth - textWidth;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawRect(recipeNameRect, rectPaint);

    canvas.clipRect(
        recipeNameRect.left + recipeNamePadding,
        recipeNameRect.top,
        recipeNameRect.right - recipeNamePadding,
        recipeNameRect.bottom
    );
    canvas.drawText(getRecipeTitle(), recipeNameOrigin.x, recipeNameOrigin.y, textPaint);
  }

  public void setRecipeId(int recipeId) {
    Recipe newRecipe = CookbookDb.instance().getRecipe(recipeId);

    if (newRecipe == null) {
      return;
    }

    recipe = newRecipe;

    RequestHelper.imageLoader().get(
        "https://dl.dropboxusercontent.com/u/10173737/Cookbook/Images/Medium/" + Integer.toString(recipe.getId()) + "-Standard-Tablet-Medium.png",
        ImageLoader.getImageListener(this, R.drawable.default_recipe_image, R.drawable.error_recipe_image));

    onSizeChanged(getWidth(), getHeight(), 0, 0);
  }

  private String getRecipeTitle() {
    if (recipe != null) {
      return recipe.getTitle();
    } else {
      return getResources().getString(R.string.unknown_recipe_name);
    }
  }
}
