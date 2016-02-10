package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import io.realm.Realm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultUpdater {

  private final ConfigBeaconUpdater beaconUpdater;
  private final ConfigGeofenceUpdater geofenceUpdater;
  private final ConfigVuforiaUpdater vuforiaUpdater;
  private final ConfigThemeUpdater themeUpdater;

  public ConfigInfoResultUpdater(
          ConfigBeaconUpdater beaconUpdater,
          ConfigGeofenceUpdater geofenceUpdater,
          ConfigVuforiaUpdater vuforiaUpdater,
          ConfigThemeUpdater themeUpdater
      ) {
    this.beaconUpdater = beaconUpdater;
    this.geofenceUpdater = geofenceUpdater;
    this.vuforiaUpdater = vuforiaUpdater;
    this.themeUpdater = themeUpdater;
  }

  public void updateConfigInfoV2(Realm realm, ConfigInfoResult config) {

    ConfigInfoResultRealm configInfoResultRealm = new ConfigInfoResultRealm();
    configInfoResultRealm.setRequestWaitTime(config.getRequestWaitTime());
    realm.copyToRealm(configInfoResultRealm);

//    if (config.supportsBeacons()){
//      RealmList<BeaconRealm> beaconRealm = beaconsToRealm(config.getBeacons());
//      realm.copyToRealm(beaconRealm);
//    }

    if (config.supportsGeofences()) {
      geofenceUpdater.saveGeofences(realm, config.getGeofences());
    }

    if (config.supportsVuforia()) {
      vuforiaUpdater.saveVuforia(realm, config.getVuforia());
    }

    if(config.supportsTheme()) {
      themeUpdater.saveTheme(realm, config.getTheme());
    }
  }

//  private RealmList<BeaconRealm> beaconsToRealm(List<Beacon> beacons) {
//    RealmList<BeaconRealm> newBeacons = new RealmList<>();
//    for (Beacon beacon:beacons){
//      newBeacons.add(beaconRealmMapper.modelToData(beacon));
//    }
//    return newBeacons;
//  }
}
