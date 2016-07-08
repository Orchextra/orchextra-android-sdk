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
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;

import java.util.NoSuchElementException;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class GeofenceEventsUpdater {

  private final Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper;
  private final OrchextraLogger orchextraLogger;

  public GeofenceEventsUpdater(
      Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper,
      OrchextraLogger orchextraLogger) {

    this.geofenceEventRealmMapper = geofenceEventRealmMapper;
    this.orchextraLogger = orchextraLogger;
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
      orchextraLogger.log("More than one region Event with same Code stored", OrchextraSDKLogLevel.ERROR);
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
    resultsToPurge.deleteAllFromRealm();
    realm.commitTransaction();
  }

  public OrchextraGeofence addActionToGeofence(Realm realm, OrchextraGeofence geofence) {
    RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
        .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, geofence.getCode())
        .findAll();

    if (results.isEmpty()) {
      orchextraLogger.log("Required region does not Exist", OrchextraSDKLogLevel.ERROR);
      throw new NoSuchElementException("Required region does not Exist");
    } else if (results.size() > 1) {
      orchextraLogger.log("More than one region Event with same Code stored", OrchextraSDKLogLevel.ERROR);
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
