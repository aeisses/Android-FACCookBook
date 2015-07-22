package github.com.foodactioncommitteecookbook.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Location;

/**
 * Request used to fetch all of the locations from the server.
 */
public class LocationRequest extends Request<List<Location>> {

  public final Gson gson = new Gson();

  public LocationRequest(int method, String url, Response.ErrorListener listener) {
    super(method, url, listener);
  }

  @Override
  protected void deliverResponse(List<Location> response) {
    CookbookDb.instance().insertLocations(response);
  }

  @Override
  protected Response<List<Location>> parseNetworkResponse(NetworkResponse response) {
    try {
      String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
      List<Location> locations = gson.fromJson(json, new TypeToken<ArrayList<Location>>() {
      }.getType());
      return Response.success(locations, HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException | JsonSyntaxException e) {
      return Response.error(new ParseError(e));
    }
  }
}
