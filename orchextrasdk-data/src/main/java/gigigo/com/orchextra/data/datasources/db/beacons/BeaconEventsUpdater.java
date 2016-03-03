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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public class BeaconEventsUpdater {

  private final Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper;
  private final Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper;

  public BeaconEventsUpdater(Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper,
      Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper) {

    this.regionEventRealmMapper = regionEventRealmMapper;
    this.beaconEventRealmMapper = beaconEventRealmMapper;
  }

  public void deleteAllBeaconsInListWithTimeStampt(Realm realm, int requestTime) {

    long timeStamptForPurge = System.currentTimeMillis() - requestTime;
    RealmResults<BeaconEventRealm> resultsToPurge = obtainPurgeResults(realm, timeStamptForPurge);
    GGGLogImpl.log("Elements to be purged: " + resultsToPurge.size());

    if (resultsToPurge.size() > 0) {
      purgeResults(realm, resultsToPurge);

      if (resultsToPurge.isEmpty()) {
        GGGLogImpl.log("purge success");
      } else {
        GGGLogImpl.log("purge fail");
      }
    }
  }

  public OrchextraBeacon storeBeaconEvent(Realm realm, OrchextraBeacon beacon) {
    BeaconEventRealm beaconEventRealm = beaconEventRealmMapper.modelToExternalClass(beacon);
    storeElement(realm, beaconEventRealm);

    GGGLogImpl.log("Stored beacon event: "
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

  public OrchextraRegion storeRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {
    BeaconRegionEventRealm beaconRegionEventRealm =
        regionEventRealmMapper.modelToExternalClass(orchextraRegion);
    storeElement(realm, beaconRegionEventRealm);
    GGGLogImpl.log("Stored region event with code " + orchextraRegion.getCode());
    return regionEventRealmMapper.externalClassToModel(beaconRegionEventRealm);
  }

  public OrchextraRegion deleteRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
        .findAll();

    if (results.size() > 1) {
      GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    } else if (results.size() == 0) {
      GGGLogImpl.log("Region candidate to be deleted: "
          + orchextraRegion.getCode()
          + "Was not previously registered");
    }

    OrchextraRegion orchextraRegionDeleted =
        regionEventRealmMapper.externalClassToModel(results.first());

    purgeResults(realm, results);

    GGGLogImpl.log("Region " + orchextraRegionDeleted.getCode() + " deleted");

    return orchextraRegionDeleted;
  }

  public OrchextraRegion addActionToRegion(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<BeaconRegionEventRealm> results = realm.where(BeaconRegionEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
        .findAll();

    if (results.isEmpty()) {
      GGGLogImpl.log("EVENT: Required region does not Exist", LogLevel.ERROR);
      throw new NoSuchElementException("Required region does not Exist");
    } else if (results.size() > 1) {
      GGGLogImpl.log("EVENT: More than one region Event with same Code stored", LogLevel.ERROR);
    } else {
      GGGLogImpl.log("EVENT: Retrieved Region with id "
          + orchextraRegion.getCode()
          + " will be associated to action "
          + orchextraRegion.getActionRelatedId());
    }

    realm.beginTransaction();
    BeaconRegionEventRealm beaconRegionEventRealm = results.first();
    beaconRegionEventRealm.setActionRelated(orchextraRegion.getActionRelatedId());
    beaconRegionEventRealm.setActionRelatedCancelable(
        (orchextraRegion.relatedActionIsCancelable()));
    realm.copyToRealmOrUpdate(beaconRegionEventRealm);
    realm.commitTransaction();

    return regionEventRealmMapper.externalClassToModel(beaconRegionEventRealm);
  }

  private RealmResults<BeaconEventRealm> obtainPurgeResults(Realm realm, long timeStamptForDelete) {
    return realm.where(BeaconEventRealm.class)
        .lessThan(BeaconEventRealm.TIMESTAMPT_FIELD_NAME, timeStamptForDelete)
        .findAll();
  }

  public List<String> obtainStoredEventBeaconCodes(Realm realm, List<OrchextraBeacon> beacons) {

    RealmResults<BeaconEventRealm> results = queryStoredBeaconEvents(realm, beacons);

    if (results.isEmpty()) {

      GGGLogImpl.log(
          "All beacons in ranging can send event," + " because they are out of request wait time");

      return Collections.emptyList();
    } else {
      List<String> codes = new ArrayList<>();

      for (BeaconEventRealm beaconEventRealm : results) {
        codes.add(beaconEventRealm.getCode());

        GGGLogImpl.log("B.UUID:"
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
    resultsToPurge.clear();
    realm.commitTransaction();
  }
}
