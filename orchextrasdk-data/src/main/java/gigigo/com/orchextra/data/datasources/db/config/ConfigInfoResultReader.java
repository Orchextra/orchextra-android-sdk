package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultReader {

  private final RealmMapper<Beacon, BeaconRealm> beaconRealmMapper;
  private final RealmMapper<Geofence, GeofenceRealm> geofencesRealmMapper;
  private final RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;
  private final RealmMapper<Theme, ThemeRealm> themeRealmMapper;

  public ConfigInfoResultReader(RealmMapper<Beacon, BeaconRealm> beaconRealmMapper,
      RealmMapper<Geofence, GeofenceRealm> geofencesRealmMapper,
      RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
      RealmMapper<Theme, ThemeRealm> themeRealmMapper) {

    this.beaconRealmMapper = beaconRealmMapper;
    this.geofencesRealmMapper = geofencesRealmMapper;
    this.vuforiaRealmMapper = vuforiaRealmMapper;
    this.themeRealmMapper = themeRealmMapper;
  }

  public ConfigInfoResult readConfigInfoV2(Realm realm) {

    ConfigInfoResultRealm config = readConfigObject(realm);

    Vuforia vuforia = vuforiaRealmMapper.dataToModel(config.getVuforia());
    Theme theme = themeRealmMapper.dataToModel(config.getTheme());
    List<Geofence> geofences = geofencesToModel(config.getGeofences());
    List<Beacon> beacons = beaconsToModel(config.getBeacons());

    return new ConfigInfoResult.ConfigInfoResultBuilder(geofences,
        beacons, theme, config.getRequestWaitTime(), vuforia).build();
  }

  private List<Beacon> beaconsToModel(List<BeaconRealm> beaconRealms) {
    List<Beacon> beacons = new ArrayList<>();
    for (BeaconRealm beaconRealm : beaconRealms){
      beacons.add(beaconRealmMapper.dataToModel(beaconRealm));
    }
    return beacons;
  }

  private List<Geofence> geofencesToModel(List<GeofenceRealm> geofencesRealm) {
    List<Geofence> geofences = new ArrayList<>();
    for (GeofenceRealm geofenceRealm : geofencesRealm){
      geofences.add(geofencesRealmMapper.dataToModel(geofenceRealm));
    }
    return geofences;
  }

  private ConfigInfoResultRealm readConfigObject(Realm realm){

    RealmResults<ConfigInfoResultRealm> result = realm.where(ConfigInfoResultRealm.class).findAll();

    if (result.size()>0){
      return result.first();
    }else{
      throw new NotFountRealmObjectException();
    }

  }

  public Beacon getBeaconByUuid(Realm realm, String id){
    BeaconRealm beaconRealm = realm.where(BeaconRealm.class).equalTo("uuid", id).findFirst();
    return beaconRealmMapper.dataToModel(beaconRealm);
  }

  public Geofence getGeofenceById(Realm realm, String id){
    GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("id", id).findFirst();
    return geofencesRealmMapper.dataToModel(geofenceRealm);
  }

  public List<Beacon> readAllRealmBeacons(Realm realm) {
    RealmResults<BeaconRealm> beaconRealms = realm.where(BeaconRealm.class).findAll();
    List<Beacon> beacons = new ArrayList<>();
    for (BeaconRealm beaconRealm : beaconRealms){
      beacons.add(beaconRealmMapper.dataToModel(beaconRealm));
    }
    return beacons;
  }

  private List<Geofence> readAllRealmGeofences(Realm realm) {
    RealmResults<GeofenceRealm> geofencesRealm = realm.where(GeofenceRealm.class).findAll();
    List<Geofence> geofences = new ArrayList<>();
    for (GeofenceRealm geofenceRealm : geofencesRealm){
      geofences.add(geofencesRealmMapper.dataToModel(geofenceRealm));
    }
    return geofences;
  }

  private Theme readRealmTheme(Realm realm) {
    RealmResults<ThemeRealm> themeRealm = realm.where(ThemeRealm.class).findAll();
    return themeRealmMapper.dataToModel(themeRealm.get(0));
  }

  private Vuforia readRealmVuforia(Realm realm) {
    RealmResults<VuforiaRealm> vuforiaRealm = realm.where(VuforiaRealm.class).findAll();
    return vuforiaRealmMapper.dataToModel(vuforiaRealm.get(0));
  }


  @Deprecated
  public ConfigInfoResult readConfigInfo(Realm realm) {

    Vuforia vuforia = readRealmVuforia(realm);
    Theme theme = readRealmTheme(realm);
    List<Geofence> geofences = readAllRealmGeofences(realm);
    List<Beacon> beacons = readAllRealmBeacons(realm);

    //TODO review 0 value for waitResponse
    return new ConfigInfoResult.ConfigInfoResultBuilder(geofences,
        beacons, theme, 0, vuforia).build();
  }

}
