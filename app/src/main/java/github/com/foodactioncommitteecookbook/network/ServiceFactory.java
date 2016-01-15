package github.com.foodactioncommitteecookbook.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 *
 */
public class ServiceFactory {

  private static final Gson gson = new GsonBuilder()
      .setDateFormat("MM:dd:yy")
      .create();

  private static final Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("https://dl.dropboxusercontent.com/")
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

  public static <T> T createService(Class<T> clazz) {
    return retrofit.create(clazz);
  }
}
