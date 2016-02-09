package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultReader {

  private final ExternalClassToModelMapper<BeaconRealm, OrchextraRegion> beaconRealmMapper;
  private final ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper;
  private final ExternalClassToModelMapper<VuforiaRealm, Vuforia> vuforiaRealmMapper;
  private final ExternalClassToModelMapper<ThemeRealm, Theme> themeRealmMapper;

  public ConfigInfoResultReader(ExternalClassToModelMapper beaconRealmMapper,
      ExternalClassToModelMapper geofencesRealmMapper,
      ExternalClassToModelMapper vuforiaRealmMapper,
      ExternalClassToModelMapper themeRealmMapper) {

    this.beaconRealmMapper = beaconRealmMapper;
    this.geofencesRealmMapper = geofencesRealmMapper;
    this.vuforiaRealmMapper = vuforiaRealmMapper;
    this.themeRealmMapper = themeRealmMapper;
  }

  public ConfigInfoResult readConfigInfoV2(Realm realm) {

    ConfigInfoResultRealm config = readConfigObject(realm);

    Vuforia vuforia = vuforiaRealmMapper.externalClassToModel(readVuforiaObject(realm));
    Theme theme = themeRealmMapper.externalClassToModel(readThemeObject(realm));
    List<OrchextraGeofence> geofences = geofencesToModel(readGeofenceObjects(realm));
    List<OrchextraRegion> beacons = beaconsToModel(readBeaconObjects(realm));

    return new ConfigInfoResult.Builder(config.getRequestWaitTime(), geofences,
        beacons, theme, vuforia).build();
  }

  private List<OrchextraRegion> beaconsToModel(List<BeaconRealm> beaconRealms) {
    List<OrchextraRegion> beacons = new ArrayList<>();
    for (BeaconRealm beaconRealm : beaconRealms){
      beacons.add(beaconRealmMapper.externalClassToModel(beaconRealm));
    }
    return beacons;
  }

  private List<OrchextraGeofence> geofencesToModel(List<GeofenceRealm> geofencesRealm) {
    List<OrchextraGeofence> geofences = new ArrayList<>();
    for (GeofenceRealm geofenceRealm : geofencesRealm){
      geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
    }
    return geofences;
  }

  private ConfigInfoResultRealm readConfigObject(Realm realm){
    ConfigInfoResultRealm configInfo = realm.where(ConfigInfoResultRealm.class).findFirst();
    if (configInfo == null) {
      configInfo = new ConfigInfoResultRealm();
    }
    return configInfo;
  }

  private VuforiaRealm readVuforiaObject(Realm realm) {
    return realm.where(VuforiaRealm.class).findFirst();
  }

  private ThemeRealm readThemeObject(Realm realm) {
    return realm.where(ThemeRealm.class).findFirst();
  }

  private List<GeofenceRealm> readGeofenceObjects(Realm realm) {
    return realm.where(GeofenceRealm.class).findAll();
  }

  private List<BeaconRealm> readBeaconObjects(Realm realm) {
    return realm.where(BeaconRealm.class).findAll();
  }

  public OrchextraRegion getBeaconByUuid(Realm realm, String id){
    BeaconRealm beaconRealm = realm.where(BeaconRealm.class).equalTo("uuid", id).findFirst();
    return beaconRealmMapper.externalClassToModel(beaconRealm);
  }

  public OrchextraGeofence getGeofenceById(Realm realm, String id){
    GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("id", id).findFirst();
    if (geofenceRealm == null) {
      throw new NotFountRealmObjectException();
    }
    return geofencesRealmMapper.externalClassToModel(geofenceRealm);
  }

  public List<OrchextraRegion> getAllBeacons(Realm realm) {
    RealmResults<BeaconRealm> beaconRealms = realm.where(BeaconRealm.class).findAll();
    List<OrchextraRegion> beacons = new ArrayList<>();
    for (BeaconRealm beaconRealm : beaconRealms){
      beacons.add(beaconRealmMapper.externalClassToModel(beaconRealm));
    }
    return beacons;
  }

  public List<OrchextraGeofence> getAllGeofences(Realm realm) {
    RealmResults<GeofenceRealm> geofencesRealm = realm.where(GeofenceRealm.class).findAll();
    List<OrchextraGeofence> geofences = new ArrayList<>();
    for (GeofenceRealm geofenceRealm : geofencesRealm){
      geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
    }
    return geofences;
  }

  public Theme getTheme(Realm realm) {
    RealmResults<ThemeRealm> themeRealm = realm.where(ThemeRealm.class).findAll();
    return themeRealmMapper.externalClassToModel(themeRealm.get(0));
  }

  private Vuforia readRealmVuforia(Realm realm) {
    RealmResults<VuforiaRealm> vuforiaRealm = realm.where(VuforiaRealm.class).findAll();
    return vuforiaRealmMapper.externalClassToModel(vuforiaRealm.get(0));
  }


  @Deprecated
  public ConfigInfoResult readConfigInfo(Realm realm) {

    Vuforia vuforia = readRealmVuforia(realm);
    Theme theme = getTheme(realm);
    List<OrchextraGeofence> geofences = getAllGeofences(realm);
    List<OrchextraRegion> beacons = getAllBeacons(realm);

    //TODO review 0 value for waitResponse
    return new ConfigInfoResult.Builder(0, geofences,
        beacons, theme, vuforia).build();
  }

}
