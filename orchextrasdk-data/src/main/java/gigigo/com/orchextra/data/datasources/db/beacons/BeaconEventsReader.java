package gigigo.com.orchextra.data.datasources.db.beacons;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
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

  public OrchextraRegion obtainRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

      RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
          .equalTo(BeaconRegionRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
          .findAll();

      if (results.size()>1) {
        GGGLogImpl.log("EVENT: More than one region Event with same Code stored", LogLevel.ERROR);
      }else if (results.size()==1){
        GGGLogImpl.log("EVENT: Recovered orchextra region " + orchextraRegion.getCode());
      }else{
        GGGLogImpl.log("EVENT: Region Event not stored " + orchextraRegion.getCode());
      }

    return regionEventRealmMapper.externalClassToModel(results.first());
  }
}
