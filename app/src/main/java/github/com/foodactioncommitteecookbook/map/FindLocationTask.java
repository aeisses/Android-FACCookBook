package github.com.foodactioncommitteecookbook.map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

/**
 * AsyncTask for grabbing the user's coordinates.
 */
public class FindLocationTask extends AsyncTask<Context, Void, Void> {

  @Override
  protected Void doInBackground(Context... params) {
    if (params.length == 0) {
      throw new RuntimeException("Can't find location without a Context.");
    }

    Looper.prepare();

    final LocationManager locationManager = (LocationManager) params[0].getSystemService(Context.LOCATION_SERVICE);

    String provider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ?
        LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER;
    locationManager.requestSingleUpdate(provider, new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {

      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      @Override
      public void onProviderEnabled(String provider) {

      }

      @Override
      public void onProviderDisabled(String provider) {

      }
    }, Looper.myLooper());
    return null;
  }

}
