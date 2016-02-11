package gigigo.com.orchextra.data.datasources.db.beacons;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconEventsUpdater {

  private final Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper;
  private final Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper;

  public BeaconEventsUpdater(
      Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper,
      Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper) {

    this.regionEventRealmMapper = regionEventRealmMapper;
    this.beaconEventRealmMapper = beaconEventRealmMapper;
  }

  public boolean deleteAllBeaconsInListWithTimeStampt(Realm realm,
      List<OrchextraBeacon> beacons, int requestTime) {

    long timeStamptForPurge = System.currentTimeMillis() - requestTime;
    RealmResults<BeaconEventRealm> resultsToPurge = obtainPurgeResults(realm, timeStamptForPurge);
    purgeResults(realm, resultsToPurge);
    return obtainIfAllNewElementsHaveBeenPurged(realm, beacons);
  }

  public OrchextraBeacon storeBeaconEvent(Realm realm, OrchextraBeacon beacon) {
    BeaconEventRealm beaconEventRealm = beaconEventRealmMapper.modelToExternalClass(beacon);
    storeElement(realm, beaconEventRealm);
    return beaconEventRealmMapper.externalClassToModel(beaconEventRealm);
  }

  public OrchextraRegion storeRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {
    BeaconRegionEventRealm beaconRegionEventRealm = regionEventRealmMapper.modelToExternalClass(
        orchextraRegion);
    storeElement(realm, beaconRegionEventRealm);
    return regionEventRealmMapper.externalClassToModel(beaconRegionEventRealm);
  }

  public OrchextraRegion deleteRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode()).findAll();

    if (results.size()>1){
        GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    }

    OrchextraRegion orchextraRegionDeleted = regionEventRealmMapper.externalClassToModel(
        results.first());

    purgeResults(realm, results);

    return orchextraRegionDeleted;
  }

  public OrchextraRegion addActionToRegion(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode()).findAll();

    if (results.isEmpty()){
      GGGLogImpl.log("Required region does not Exist", LogLevel.ERROR);
      throw new NoSuchElementException("Required region does not Exist");
    }else if(results.size()>1){
      GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    }

    BeaconRegionEventRealm beaconRegionEventRealm = results.first();
    beaconRegionEventRealm.setActionRelated(orchextraRegion.getActionRelated());
    storeElement(realm,beaconRegionEventRealm);

    return regionEventRealmMapper.externalClassToModel(beaconRegionEventRealm);
  }

  private RealmResults<BeaconEventRealm> obtainPurgeResults(Realm realm, long timeStamptForDelete) {
    return realm.where(BeaconEventRealm.class).lessThan(BeaconEventRealm.TIMESTAMPT_FIELD_NAME,
        timeStamptForDelete).findAll();
  }

  private boolean obtainIfAllNewElementsHaveBeenPurged(Realm realm, List<OrchextraBeacon> beacons) {

    RealmQuery<BeaconEventRealm> query = realm.where(BeaconEventRealm.class);

    for(OrchextraBeacon beacon : beacons) {
      query = query.or().equalTo(BeaconEventRealm.CODE_FIELD_NAME, beacon.getCode())
          .equalTo(BeaconEventRealm.DISTANCE_FIELD_NAME, beacon.getBeaconDistance().getStringValue());
    }

    RealmResults<BeaconEventRealm> results = query.findAll();

    return results.isEmpty();
  }

  private void storeElement(Realm realm, RealmObject element) {
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(element);
    realm.commitTransaction();
  }

  private void purgeResults(Realm realm, RealmResults resultsToPurge) {
    realm.beginTransaction();
    resultsToPurge.clear();
    realm.commitTransaction();
  }
}
