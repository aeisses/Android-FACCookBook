package github.com.foodactioncommitteecookbook.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

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

  @AfterViews
  public void initMap() {
    map.getMapAsync(this);
  }

  @OptionsItem
  public void actionRegionsSelected() {
    RegionSelectDialogFragment regionSelect = new RegionSelectDialogFragment();
    regionSelect.show(getFragmentManager(), "RegionSelectDialog");
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
