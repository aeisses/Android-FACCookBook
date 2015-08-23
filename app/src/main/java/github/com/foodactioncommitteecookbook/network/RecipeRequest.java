package github.com.foodactioncommitteecookbook.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 * Request used to fetch all recipes from the server.
 */
public class RecipeRequest extends Request<List<Recipe>> {
    private Gson gson = new GsonBuilder().setDateFormat("MM:dd:yyyy").create();

    public RecipeRequest() {
        super(Method.GET, "https://dl.dropboxusercontent.com/u/10173737/Cookbook/recipes.json", new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().postSticky(new ErrorEvent(error));
            }
        });
    }

    @Override
    protected Response<List<Recipe>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Recipes recipes = gson.fromJson(json, Recipes.class);
            return Response.success(recipes.getRecipes(), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List<Recipe> response) {
        EventBus.getDefault().postSticky(new CompleteEvent(response));
    }

    public static class ErrorEvent {
        private final VolleyError error;

        private ErrorEvent(VolleyError error) {
            this.error = error;
        }

        public VolleyError getVolleyError() {
            return error;
        }
    }

    public static class CompleteEvent {
        private final List<Recipe> recipes;

        private CompleteEvent(List<Recipe> recipes) {
            this.recipes = recipes;
        }

        public List<Recipe> getRecipes() {
            return recipes;
        }
    }

    public static class Recipes {
        @SerializedName("recipes")
        private List<Recipe> recipes;

        public List<Recipe> getRecipes() {
            return recipes;
        }
    }

}
