package github.com.foodactioncommitteecookbook.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.List;

import github.com.foodactioncommitteecookbook.model.Recipe;

/**
 * Request used to fetch all recipes from the server.
 */
public class RecipeRequest extends Request<List<Recipe>> {
    public RecipeRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<List<Recipe>> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(List<Recipe> response) {

    }
}
