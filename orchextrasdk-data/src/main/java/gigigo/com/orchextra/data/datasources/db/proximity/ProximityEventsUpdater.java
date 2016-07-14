/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.proximity;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionEventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ProximityEventsUpdater {

  private final Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper;
  private final Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper;
  private final OrchextraLogger orchextraLogger;

  public ProximityEventsUpdater(Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper,
                                Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper,
                                OrchextraLogger orchextraLogger) {

    this.regionEventRealmMapper = regionEventRealmMapper;
    this.beaconEventRealmMapper = beaconEventRealmMapper;
    this.orchextraLogger = orchextraLogger;

  }

  public synchronized void deleteAllBeaconsInListWithTimeStampt(Realm realm, int requestTime) {

    long timeStamptForPurge = System.currentTimeMillis() - requestTime;
    RealmResults<BeaconEventRealm> resultsToPurge = obtainPurgeResults(realm, timeStamptForPurge);
    orchextraLogger.log("Elements to be purged: " + resultsToPurge.size());

    if (resultsToPurge.size() > 0) {
      purgeResults(realm, resultsToPurge);

      if (resultsToPurge.isEmpty()) {
        orchextraLogger.log("purge success");
      } else {
        orchextraLogger.log("purge fail");
      }
    }
  }

  public synchronized OrchextraBeacon storeBeaconEvent(Realm realm, OrchextraBeacon beacon) {
    BeaconEventRealm beaconEventRealm = beaconEventRealmMapper.modelToExternalClass(beacon);
    storeElement(realm, beaconEventRealm);

    orchextraLogger.log("Stored beacon event: "
        + beaconEventRealm.getUuid()
        + "_"
        +
        beaconEventRealm.getMayor()
        + "_"
        + beaconEventRealm.getMinor()
        + ":"
        + beaconEventRealm.getBeaconDistance());

    return beaconEventRealmMapper.externalClassToModel(beaconEventRealm);
  }

  public synchronized OrchextraRegion storeRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {
    RegionEventRealm regionEventRealm =
        regionEventRealmMapper.modelToExternalClass(orchextraRegion);
    storeElement(realm, regionEventRealm);
    orchextraLogger.log("Stored region event with code " + orchextraRegion.getCode());
    return regionEventRealmMapper.externalClassToModel(regionEventRealm);
  }

  public synchronized OrchextraRegion deleteRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<RegionEventRealm> results = realm.where(RegionEventRealm.class)
        .equalTo(RegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
        .findAll();

    if (results.size() > 1) {
      orchextraLogger.log("More than one region Event with same Code stored", OrchextraSDKLogLevel.ERROR);
    } else if (results.size() == 0) {
      orchextraLogger.log("Region candidate to be deleted: "
          + orchextraRegion.getCode()
          + "Was not previously registered");
    }

    OrchextraRegion orchextraRegionDeleted =
        regionEventRealmMapper.externalClassToModel(results.first());

    purgeResults(realm, results);

    orchextraLogger.log("Region " + orchextraRegionDeleted.getCode() + " deleted");

    return orchextraRegionDeleted;
  }

  public synchronized OrchextraRegion addActionToRegion(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<RegionEventRealm> results = realm.where(RegionEventRealm.class)
        .equalTo(RegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
        .findAll();

    if (results.isEmpty()) {
      orchextraLogger.log("EVENT: Required region does not Exist", OrchextraSDKLogLevel.ERROR);
      throw new NoSuchElementException("Required region does not Exist");
    } else if (results.size() > 1) {
      orchextraLogger.log("EVENT: More than one region Event with same Code stored", OrchextraSDKLogLevel.ERROR);
    } else {
      orchextraLogger.log("EVENT: Retrieved Region with id "
          + orchextraRegion.getCode()
          + " will be associated to action "
          + orchextraRegion.getActionRelatedId());
    }

    realm.beginTransaction();
    RegionEventRealm regionEventRealm = results.first();
    regionEventRealm.setActionRelated(orchextraRegion.getActionRelatedId());
    regionEventRealm.setActionRelatedCancelable(
        (orchextraRegion.relatedActionIsCancelable()));
    realm.copyToRealmOrUpdate(regionEventRealm);
    realm.commitTransaction();

    return regionEventRealmMapper.externalClassToModel(regionEventRealm);
  }

  private synchronized RealmResults<BeaconEventRealm> obtainPurgeResults(Realm realm, long timeStamptForDelete) {
    return realm.where(BeaconEventRealm.class)
        .lessThan(BeaconEventRealm.TIMESTAMPT_FIELD_NAME, timeStamptForDelete)
        .findAll();
  }

  public synchronized List<String> obtainStoredEventBeaconCodes(Realm realm, List<OrchextraBeacon> beacons) {

    RealmResults<BeaconEventRealm> results = queryStoredBeaconEvents(realm, beacons);

    if (results.isEmpty()) {

      orchextraLogger.log(
          "All beacons in ranging can send event," + " because they are out of request wait time");

      return Collections.emptyList();
    } else {
      List<String> codes = new ArrayList<>();

      for (BeaconEventRealm beaconEventRealm : results) {
        codes.add(beaconEventRealm.getCode());

        orchextraLogger.log("B.UUID:"
            + beaconEventRealm.getUuid()
            + "_"
            + beaconEventRealm.getMayor()
            + "_"
            + beaconEventRealm.getMinor()
            + ":"
            + beaconEventRealm.getBeaconDistance()
            +
            "-->still under RWT");
      }

      return codes;
    }
  }

  private RealmResults<BeaconEventRealm> queryStoredBeaconEvents(Realm realm,
      List<OrchextraBeacon> beacons) {
    RealmQuery<BeaconEventRealm> query = realm.where(BeaconEventRealm.class);

    for (int i = 0; i < beacons.size(); i++) {

      if (i > 0) {
        query = query.or();
      }

      query = query.equalTo(BeaconEventRealm.CODE_FIELD_NAME, beacons.get(i).getCode())
          .equalTo(BeaconEventRealm.DISTANCE_FIELD_NAME,
              beacons.get(i).getBeaconDistance().getStringValue());
    }

    return query.findAll();
  }

  private void storeElement(Realm realm, RealmObject element) {
    realm.beginTransaction();
    realm.copyToRealmOrUpdate(element);
    realm.commitTransaction();
  }

  private void purgeResults(Realm realm, RealmResults resultsToPurge) {
    realm.beginTransaction();
    resultsToPurge.clear();//.deleteAllFromRealm();
    realm.commitTransaction();
  }
}
