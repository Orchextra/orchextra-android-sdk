package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultUpdater {

  private final RealmMapper<OrchextraRegion, BeaconRealm> beaconRealmMapper;
  private final RealmMapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper;
  private final RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;
  private final RealmMapper<Theme, ThemeRealm> themeRealmMapper;

  public ConfigInfoResultUpdater(RealmMapper<OrchextraRegion, BeaconRealm> beaconRealmMapper,
      RealmMapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper,
      RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
      RealmMapper<Theme, ThemeRealm> themeRealmMapper) {

    this.beaconRealmMapper = beaconRealmMapper;
    this.geofencesRealmMapper = geofencesRealmMapper;
    this.vuforiaRealmMapper = vuforiaRealmMapper;
    this.themeRealmMapper = themeRealmMapper;
  }

  public void updateConfigInfoV2(Realm realm, ConfigInfoResult config) {
    clearDatabase(realm);

    ConfigInfoResultRealm configInfoResultRealm = new ConfigInfoResultRealm();
    configInfoResultRealm.setRequestWaitTime(config.getRequestWaitTime());
    realm.copyToRealm(configInfoResultRealm);

    if (config.supportsBeacons()){
      RealmList<BeaconRealm> beaconRealm = beaconsToRealm(config.getRegions());
      realm.copyToRealm(beaconRealm);
    }

    if (config.supportsGeofences()){
      RealmList<GeofenceRealm> geofenceRealm = geofencesToRealm(config.getGeofences());
      realm.copyToRealm(geofenceRealm);
    }

    if (config.supportsVuforia()){
      VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToData(config.getVuforia());
      realm.copyToRealm(vuforiaRealm);
    }

    if (config.supportsTheme()){
      ThemeRealm themeRealm = themeRealmMapper.modelToData(config.getTheme());
      realm.copyToRealm(themeRealm);
    }
  }

  private void clearDatabase(Realm realm) {
    realm.clear(ConfigInfoResultRealm.class);
    realm.clear(BeaconRealm.class);
    realm.clear(GeofenceRealm.class);
    realm.clear(VuforiaRealm.class);
    realm.clear(ThemeRealm.class);
  }

  private RealmList<BeaconRealm> beaconsToRealm(List<OrchextraRegion> beacons) {
    RealmList<BeaconRealm> newBeacons = new RealmList<>();
    for (OrchextraRegion beacon:beacons){
      newBeacons.add(beaconRealmMapper.modelToData(beacon));
    }
    return newBeacons;
  }

  private RealmList<GeofenceRealm> geofencesToRealm(List<OrchextraGeofence> geofences) {
    RealmList<GeofenceRealm> newGeofences = new RealmList<>();
    for (OrchextraGeofence geofence:geofences){
      newGeofences.add(geofencesRealmMapper.modelToData(geofence));
    }
    return newGeofences;
  }

  @Deprecated
  public void updateConfigInfo(Realm realm, ConfigInfoResult configInfoResult) {

    if (configInfoResult.supportsBeacons()){
      updateBeacons(realm, configInfoResult.getRegions());
    }else{
      clearBeacons(realm);
    }

    if (configInfoResult.supportsGeofences()){
      updateGeofences(realm, configInfoResult.getGeofences());
    }else{
      clearGeofences(realm);
    }

    if (configInfoResult.supportsVuforia()){
      updateVuforia(realm, configInfoResult.getVuforia());
    }else{
      clearVuforia(realm);
    }

    if (configInfoResult.supportsTheme()){
      updateTheme(realm, configInfoResult.getTheme());
    }else{
      clearTheme(realm);
    }

  }

  private void updateTheme(Realm realm, Theme theme) {
    clearTheme(realm);
    realm.copyToRealm(themeRealmMapper.modelToData(theme));
  }

  private void clearTheme(Realm realm) {
    RealmResults<ThemeRealm> themes = realm.where(ThemeRealm.class).findAll();
    themes.clear();
  }

  private void updateVuforia(Realm realm, Vuforia vuforia) {
    clearVuforia(realm);
    realm.copyToRealm(vuforiaRealmMapper.modelToData(vuforia));
  }

  private void clearVuforia(Realm realm) {
    RealmResults<VuforiaRealm> vuforia = realm.where(VuforiaRealm.class).findAll();
    vuforia.clear();
  }

  private void updateGeofences(Realm realm, List<OrchextraGeofence> geofences) {
    clearGeofences(realm);
    RealmList<GeofenceRealm> newGeofences = new RealmList<>();
    for (OrchextraGeofence geofence:geofences){
      newGeofences.add(geofencesRealmMapper.modelToData(geofence));
    }
    realm.copyToRealm(newGeofences);
  }

  private void clearGeofences(Realm realm) {
    RealmResults<GeofenceRealm> dbGeofences = realm.where(GeofenceRealm.class).findAll();
    dbGeofences.clear();
  }

  private void updateBeacons(Realm realm, List<OrchextraRegion> beacons) {
    clearBeacons(realm);
    RealmList<BeaconRealm> newBeacons = new RealmList<>();
    for (OrchextraRegion beacon:beacons){
      newBeacons.add(beaconRealmMapper.modelToData(beacon));
    }
    realm.copyToRealm(newBeacons);
  }

  private void clearBeacons(Realm realm) {
    RealmResults<BeaconRealm> dbBeacons = realm.where(BeaconRealm.class).findAll();
    dbBeacons.clear();
  }

}
