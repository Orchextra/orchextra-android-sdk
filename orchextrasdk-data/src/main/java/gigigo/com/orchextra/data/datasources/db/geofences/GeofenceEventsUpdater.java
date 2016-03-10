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

package gigigo.com.orchextra.data.datasources.db.geofences;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.NoSuchElementException;

public class GeofenceEventsUpdater {

  private final Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper;

  public GeofenceEventsUpdater(
      Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {

    this.geofenceEventRealmMapper = geofenceEventRealmMapper;
  }

  public OrchextraGeofence storeGeofenceEvent(Realm realm, OrchextraGeofence geofence) {
    GeofenceEventRealm geofenceEventRealm = geofenceEventRealmMapper.modelToExternalClass(geofence);
    storeElement(realm, geofenceEventRealm);
    return geofenceEventRealmMapper.externalClassToModel(geofenceEventRealm);
  }

  public OrchextraGeofence deleteGeofenceEvent(Realm realm, OrchextraGeofence geofence) {

    RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, geofence.getCode())
        .findAll();

    if (results.size() > 1) {
      GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    } else if (results.size() == 0) {
      throw new NotFountRealmObjectException();
    }

    OrchextraGeofence removedGeofence =
        geofenceEventRealmMapper.externalClassToModel(results.first());

    purgeResults(realm, results);

    return removedGeofence;
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

  public OrchextraGeofence addActionToGeofence(Realm realm, OrchextraGeofence geofence) {
    RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, geofence.getCode())
        .findAll();

    if (results.isEmpty()) {
      GGGLogImpl.log("Required region does not Exist", LogLevel.ERROR);
      throw new NoSuchElementException("Required region does not Exist");
    } else if (results.size() > 1) {
      GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    }

    realm.beginTransaction();
    GeofenceEventRealm geofenceEventRealm = results.first();
    geofenceEventRealm.setActionRelated(geofence.getActionRelatedId());
    geofenceEventRealm.setActionRelatedCancelable(geofence.relatedActionIsCancelable());

    realm.copyToRealmOrUpdate(geofenceEventRealm);
    realm.commitTransaction();

    return geofenceEventRealmMapper.externalClassToModel(geofenceEventRealm);
  }
}
