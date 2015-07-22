package github.com.foodactioncommitteecookbook.map;

/**
 * Event posted to the bus when a region is selected on the Map activity.
 */
public class RegionSelectedEvent {
  private Region region;

  public RegionSelectedEvent(Region region) {
    this.region = region;
  }

  public Region getRegion() {
    return region;
  }
}
