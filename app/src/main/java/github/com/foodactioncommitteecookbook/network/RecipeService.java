package github.com.foodactioncommitteecookbook.network;

import java.util.List;

import github.com.foodactioncommitteecookbook.model.Location;
import github.com.foodactioncommitteecookbook.model.RecipeList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 */
public interface RecipeService {
  @GET("u/19713116/foundation/recipies.json") Observable<RecipeList> recipes();

  @GET("u/19713116/foundation/update.json")
  Observable<RecipeList> recipes(@Query("since") String formattedDate);

  @GET("u/95002502/foundation/location.json") Observable<List<Location>> locations();

  @GET("u/95002502/foundation/location.json")
  Observable<List<Location>> locationsSince(@Query("since") String formattedDate);

  @GET("/u/95002502/foundation/featured.json") Observable<List<Integer>> featured();

  @GET("/u/95002502/foundation/popular.json") Observable<List<Integer>> popular();

  @GET("/u/95002502/foundation/purchased.json") Observable<List<Integer>> purchased();

  // Not available yet
  // @GET("/u/95002502/foundation/information.json") Call<List<Integer>> information();
}
