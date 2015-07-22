package github.com.foodactioncommitteecookbook;

import android.app.ActionBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import github.com.foodactioncommitteecookbook.db.CookbookDb;
import github.com.foodactioncommitteecookbook.model.Location;

/**
 * Shows a map of the province with local food sources annotated.
 */
@EActivity(R.layout.activity_map)
public class MapActivity extends BaseActivity implements OnMapReadyCallback {

//  static List<MarkerOptions> sPlaces = new LinkedList<>();
//
//  static {
//    {
//      sPlaces.add(new MarkerOptions()
//          .title("Maritime Center")
//          .snippet("Salesforce Office")
//          .position(new LatLng(44.644444, -63.571944)));
//
//      sPlaces.add(new MarkerOptions()
//          .title("Quinpool")
//          .snippet("A place with stuff")
//          .position(new LatLng(44.645234, -63.596510)));
//
//      sPlaces.add(new MarkerOptions()
//          .title("Charles Street")
//          .snippet("idk why you would want to go here")
//          .position(new LatLng(44.655010, -63.591626)));
//
//      sPlaces.add(new MarkerOptions()
//          .title("Citadel Hill")
//          .snippet("Home of the noon cannon. BOOM!")
//          .position(new LatLng(44.647553, -63.580687)));
//
//      sPlaces.add(new MarkerOptions()
//          .title("Georges Island")
//          .snippet("Halifax's island time capsule full of old stuff")
//          .position(new LatLng(44.641178, -63.559381)));
//    }
//  }

  @FragmentById
  MapFragment map;

  @AfterViews
  public void initMap() {
    map.getMapAsync(this);
  }

  @AfterViews
  public void initActionBar() {
    ActionBar actionBar = getActionBar();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    for (Location location : CookbookDb.instance().getLocations()) {
      MarkerOptions marker = new MarkerOptions()
          .title(location.getTitle())
          .snippet(location.getMapSnippet())
          .position(location.getCoordinate());
      googleMap.addMarker(marker);
    }
  }
}
