package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class RealmPoint extends RealmObject {

  private double lat;

  private double lng;

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }
}
