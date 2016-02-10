package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;

import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultUpdater {

  private final ModelToExternalClassMapper<OrchextraRegion, BeaconRegionRealm> regionRealmMapper;
  private final ModelToExternalClassMapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper;
  private final ModelToExternalClassMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;
  private final ModelToExternalClassMapper<Theme, ThemeRealm> themeRealmMapper;

  public ConfigInfoResultUpdater(ModelToExternalClassMapper<OrchextraRegion, BeaconRegionRealm> regionRealmMapper,
      ModelToExternalClassMapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper,
      ModelToExternalClassMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
      ModelToExternalClassMapper<Theme, ThemeRealm> themeRealmMapper) {

    this.regionRealmMapper = regionRealmMapper;
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
      RealmList<BeaconRegionRealm> beaconRegionRealm = beaconsToRealm(config.getRegions());
      realm.copyToRealm(beaconRegionRealm);
    }

    if (config.supportsGeofences()){
      RealmList<GeofenceRealm> geofenceRealm = geofencesToRealm(config.getGeofences());
      realm.copyToRealm(geofenceRealm);
    }

    if (config.supportsVuforia()){
      VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToExternalClass(config.getVuforia());
      realm.copyToRealm(vuforiaRealm);
    }

    if (config.supportsTheme()){
      ThemeRealm themeRealm = themeRealmMapper.modelToExternalClass(config.getTheme());
      realm.copyToRealm(themeRealm);
    }
  }

  private void clearDatabase(Realm realm) {
    realm.clear(ConfigInfoResultRealm.class);
    realm.clear(BeaconRegionRealm.class);
    realm.clear(GeofenceRealm.class);
    realm.clear(VuforiaRealm.class);
    realm.clear(ThemeRealm.class);
  }

  private RealmList<BeaconRegionRealm> beaconsToRealm(List<OrchextraRegion> regions) {
    RealmList<BeaconRegionRealm> newRegions = new RealmList<>();
    for (OrchextraRegion region:regions){
      newRegions.add(regionRealmMapper.modelToExternalClass(region));
    }
    return newRegions;
  }

  private RealmList<GeofenceRealm> geofencesToRealm(List<OrchextraGeofence> geofences) {
    RealmList<GeofenceRealm> newGeofences = new RealmList<>();
    for (OrchextraGeofence geofence:geofences){
      newGeofences.add(geofencesRealmMapper.modelToExternalClass(geofence));
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
    realm.copyToRealm(themeRealmMapper.modelToExternalClass(theme));
  }

  private void clearTheme(Realm realm) {
    RealmResults<ThemeRealm> themes = realm.where(ThemeRealm.class).findAll();
    themes.clear();
  }

  private void updateVuforia(Realm realm, Vuforia vuforia) {
    clearVuforia(realm);
    realm.copyToRealm(vuforiaRealmMapper.modelToExternalClass(vuforia));
  }

  private void clearVuforia(Realm realm) {
    RealmResults<VuforiaRealm> vuforia = realm.where(VuforiaRealm.class).findAll();
    vuforia.clear();
  }

  private void updateGeofences(Realm realm, List<OrchextraGeofence> geofences) {
    clearGeofences(realm);
    RealmList<GeofenceRealm> newGeofences = new RealmList<>();
    for (OrchextraGeofence geofence:geofences){
      newGeofences.add(geofencesRealmMapper.modelToExternalClass(geofence));
    }
    realm.copyToRealm(newGeofences);
  }

  private void clearGeofences(Realm realm) {
    RealmResults<GeofenceRealm> dbGeofences = realm.where(GeofenceRealm.class).findAll();
    dbGeofences.clear();
  }

  private void updateBeacons(Realm realm, List<OrchextraRegion> regions) {
    clearBeacons(realm);
    RealmList<BeaconRegionRealm> newRegions = new RealmList<>();
    for (OrchextraRegion region:regions){
      newRegions.add(regionRealmMapper.modelToExternalClass(region));
    }
    realm.copyToRealm(newRegions);
  }

  private void clearBeacons(Realm realm) {
    RealmResults<BeaconRegionRealm> dbBeacons = realm.where(BeaconRegionRealm.class).findAll();
    dbBeacons.clear();
  }

}
