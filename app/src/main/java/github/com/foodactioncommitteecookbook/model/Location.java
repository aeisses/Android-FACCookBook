package github.com.foodactioncommitteecookbook.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Model class for Location data.
 */
public class Location {

  @SerializedName("locationId")
  private int locationId;

  @SerializedName("title")
  private String title;

  @SerializedName("addedDate")
  private Date addedDate;

  @SerializedName("latitude")
  private float latitude;

  @SerializedName("longitude")
  private float longitude;

  @SerializedName("address")
  private String address;

  @SerializedName("email")
  private String email;

  @SerializedName("phone")
  private String phone;

  @SerializedName("story")
  private String story;

  public Location(int locationId, String title, Date addedDate, float latitude, float longitude, String address, String email, String phone, String story) {
    this.locationId = locationId;
    this.title = title;
    this.addedDate = addedDate;
    this.latitude = latitude;
    this.longitude = longitude;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.story = story;
  }

  public int getLocationId() {
    return locationId;
  }

  public String getTitle() {
    return title;
  }

  public Date getAddedDate() {
    return addedDate;
  }

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getStory() {
    return story;
  }

  public LatLng getCoordinate() {
    return new LatLng(latitude, longitude);
  }

  public String getMapSnippet() {
    return address + "\n" +
        phone + "\n" +
        email + "\n\n" +
        story;
  }
}
