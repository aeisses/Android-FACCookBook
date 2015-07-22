package github.com.foodactioncommitteecookbook.map;

import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import de.greenrobot.event.EventBus;
import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;
import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Location;

/**
 * Shows a map of the province with local food sources annotated.
 */
@EActivity(R.layout.activity_map)
@OptionsMenu(R.menu.map_actions)
public class MapActivity extends BaseActivity implements OnMapReadyCallback {

  @FragmentById
  MapFragment map;

  @Nullable
  private GoogleMap googleMap;

  @Nullable
  private android.location.Location initialLocation;

  @AfterViews
  public void initMap() {
    map.getMapAsync(this);
    EventBus.getDefault().register(this);

    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    String provider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ?
        LocationManager.GPS_PROVIDER : LocationManager.NETWORK_PROVIDER;
    initialLocation = locationManager.getLastKnownLocation(provider);

  }

  @OptionsItem
  public void actionRegionsSelected() {
    RegionSelectDialogFragment regionSelect = new RegionSelectDialogFragment();
    regionSelect.show(getFragmentManager(), "RegionSelectDialog");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
    for (Location location : CookbookDb.instance().getLocations()) {
      MarkerOptions marker = new MarkerOptions()
          .title(location.getTitle())
          .snippet(location.getMapSnippet())
          .position(location.getCoordinate());
      googleMap.addMarker(marker);
    }

    if (initialLocation == null) {
      goToRegion(Region.Halifax);
    } else {
      LatLng coordinates = new LatLng(initialLocation.getLatitude(), initialLocation.getLongitude());
      googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 9));
      Toast.makeText(this, R.string.map_currentlocation, Toast.LENGTH_SHORT).show();
    }
  }

  public void goToRegion(Region region) {
    if (googleMap == null) {
      return;
    }

    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(region.getCoordinates(), region.getZoom()));
  }

  @SuppressWarnings("unused")
  public void onEvent(final RegionSelectedEvent event) {
    goToRegion(event.getRegion());
  }
}
