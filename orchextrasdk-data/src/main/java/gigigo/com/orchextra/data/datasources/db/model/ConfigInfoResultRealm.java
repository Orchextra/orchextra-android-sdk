package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultRealm extends RealmObject {

  private RealmList<BeaconRealm> beacons;
  private RealmList<GeofenceRealm> geofences;
  private VuforiaRealm vuforia;
  private ThemeRealm theme;

  private int requestWaitTime;

  public RealmList<BeaconRealm> getBeacons() {
    return beacons;
  }

  public void setBeacons(RealmList<BeaconRealm> beacons) {
    this.beacons = beacons;
  }

  public RealmList<GeofenceRealm> getGeofences() {
    return geofences;
  }

  public void setGeofences(RealmList<GeofenceRealm> geofences) {
    this.geofences = geofences;
  }

  public VuforiaRealm getVuforia() {
    return vuforia;
  }

  public void setVuforia(VuforiaRealm vuforia) {
    this.vuforia = vuforia;
  }

  public ThemeRealm getTheme() {
    return theme;
  }

  public void setTheme(ThemeRealm theme) {
    this.theme = theme;
  }

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }
}
