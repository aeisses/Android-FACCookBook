package github.com.foodactioncommitteecookbook.map;

import com.google.android.gms.maps.model.LatLng;

import github.com.foodactioncommitteecookbook.R;

/**
 * An enumeration of regions in Nova Scotia.
 */
public enum Region {
  AnnapolisValley(R.string.region_annapolisvalley, new LatLng(44.841793, -64.440108), 8),
  Antigonish(R.string.region_antigonish, new LatLng(45.579708, -61.706384), 8),
  CapeBreton(R.string.region_capebreton, new LatLng(46.085546, -60.064748), 8),
  EasternShore(R.string.region_easternshore, new LatLng(45.100148, -61.696905), 8),
  Halifax(R.string.region_halifax, new LatLng(44.670000, -63.610000), 11),
  NorthNova(R.string.region_northnova, new LatLng(45.733652, -63.430210), 8),
  SouthShore(R.string.region_southshore, new LatLng(44.358912, -64.212215), 8),
  Yarmouth(R.string.region_yarmouth, new LatLng(44.088340, -65.324790), 8);


  private int resId;
  private LatLng coordinates;
  private int zoom;

  Region(int resId, LatLng coordinates, int zoom) {
    this.resId = resId;
    this.coordinates = coordinates;
    this.zoom = zoom;
  }

  public int getResourceId() {
    return resId;
  }

  public LatLng getCoordinates() {
    return coordinates;
  }

  public int getZoom() {
    return zoom;
  }
}
