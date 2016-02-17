package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
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

  private final ExternalClassToModelMapper<BeaconRegionRealm, OrchextraRegion> regionRealmMapper;
  private final ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper;
  private final ExternalClassToModelMapper<VuforiaRealm, Vuforia> vuforiaRealmMapper;
  private final ExternalClassToModelMapper<ThemeRealm, Theme> themeRealmMapper;

  public ConfigInfoResultReader(ExternalClassToModelMapper regionRealmMapper,
      ExternalClassToModelMapper geofencesRealmMapper,
      ExternalClassToModelMapper vuforiaRealmMapper,
      ExternalClassToModelMapper themeRealmMapper) {

    this.regionRealmMapper = regionRealmMapper;
    this.geofencesRealmMapper = geofencesRealmMapper;
    this.vuforiaRealmMapper = vuforiaRealmMapper;
    this.themeRealmMapper = themeRealmMapper;
  }

  public ConfigInfoResult readConfigInfo(Realm realm) {

    ConfigInfoResultRealm config = readConfigObject(realm);

    Vuforia vuforia = vuforiaRealmMapper.externalClassToModel(readVuforiaObject(realm));
    Theme theme = themeRealmMapper.externalClassToModel(readThemeObject(realm));
    List<OrchextraGeofence> geofences = geofencesToModel(readGeofenceObjects(realm));
    List<OrchextraRegion> regions = regionsToModel(readRegionsObjects(realm));

    return new ConfigInfoResult.Builder(config.getRequestWaitTime(), geofences,
        regions, theme, vuforia).build();
  }

  private List<OrchextraRegion> regionsToModel(List<BeaconRegionRealm> beaconRegionRealms) {
    List<OrchextraRegion> regions = new ArrayList<>();
    for (BeaconRegionRealm beaconRegionRealm : beaconRegionRealms){
      regions.add(regionRealmMapper.externalClassToModel(beaconRegionRealm));
    }
    return regions;
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

  private List<BeaconRegionRealm> readRegionsObjects(Realm realm) {
    return realm.where(BeaconRegionRealm.class).findAll();
  }

  public OrchextraGeofence getGeofenceById(Realm realm, String geofenceId){
    GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("code", geofenceId).findFirst();

    if (geofenceRealm == null) {
      throw new NotFountRealmObjectException();
    }
    return geofencesRealmMapper.externalClassToModel(geofenceRealm);
  }

  public List<OrchextraRegion> getAllRegions(Realm realm) {
    RealmResults<BeaconRegionRealm> regionRealms = realm.where(BeaconRegionRealm.class).findAll();
    List<OrchextraRegion> regions = new ArrayList<>();
    for (BeaconRegionRealm beaconRegionRealm : regionRealms){
      regions.add(regionRealmMapper.externalClassToModel(beaconRegionRealm));
    }
    return regions;
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

}
