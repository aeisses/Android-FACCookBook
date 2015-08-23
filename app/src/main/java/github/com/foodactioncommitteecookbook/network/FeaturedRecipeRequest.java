package github.com.foodactioncommitteecookbook.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;

import de.greenrobot.event.EventBus;
import github.com.foodactioncommitteecookbook.model.FeaturedRecipe;

/**
 * Request used to fetch all of the locations from the server.
 */
public class FeaturedRecipeRequest extends Request<FeaturedRecipe> {

  public final Gson gson = new Gson();

  public FeaturedRecipeRequest () {
    super(Method.GET, "https://dl.dropboxusercontent.com/u/10173737/Cookbook/featured_recipe.json", new Response.ErrorListener() {
      @Override
      public void onErrorResponse (VolleyError error) {
        EventBus.getDefault().postSticky(new ErrorEvent(error));
      }
    });
  }

  @Override
  protected void deliverResponse (FeaturedRecipe response) {
    EventBus.getDefault().postSticky(new CompleteEvent(response.getRecipeId()));
  }

  @Override
  protected Response<FeaturedRecipe> parseNetworkResponse (NetworkResponse response) {
    try {
      String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
      FeaturedRecipe locations = gson.fromJson(json, new TypeToken<FeaturedRecipe>() {
      }.getType());
      return Response.success(locations, HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException | JsonSyntaxException e) {
      return Response.error(new ParseError(e));
    }
  }


  public static class ErrorEvent {
    private final VolleyError error;

    private ErrorEvent (VolleyError error) {
      this.error = error;
    }

    public VolleyError getVolleyError () {
      return error;
    }
  }


  public static class CompleteEvent {
    private final int featuredId;

    private CompleteEvent (int featuredId) {
      this.featuredId = featuredId;
    }

    public int getFeaturedId () {
      return featuredId;
    }
  }

}
