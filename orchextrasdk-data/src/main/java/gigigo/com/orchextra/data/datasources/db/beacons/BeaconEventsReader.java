package gigigo.com.orchextra.data.datasources.db.beacons;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconEventsReader {

  private final Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper;

  public BeaconEventsReader(
      Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper) {
    this.regionEventRealmMapper = regionEventRealmMapper;
  }

  public boolean isBeaconEventStored(Realm realm, OrchextraBeacon beacon) {
    boolean result = false;
    try {
      RealmResults<BeaconEventRealm> realms = realm.where(BeaconEventRealm.class)
          .equalTo(BeaconEventRealm.CODE_FIELD_NAME, beacon.getCode())
          .equalTo(BeaconEventRealm.DISTANCE_FIELD_NAME, beacon.getBeaconDistance().getStringValue())
          .findAll();
      result = (!realms.isEmpty());
    }catch (Exception e){
      GGGLogImpl.log(e.getMessage(), LogLevel.ERROR);
    }finally {
    return result;
    }
  }

  public OrchextraRegion obtainRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

      RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
          .equalTo(BeaconRegionRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
          .findAll();

      if (results.size()>1) {
        GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
      }

    return regionEventRealmMapper.externalClassToModel(results.first());
  }
}
