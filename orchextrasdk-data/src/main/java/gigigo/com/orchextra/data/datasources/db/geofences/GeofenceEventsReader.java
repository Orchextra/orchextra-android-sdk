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

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class GeofenceEventsReader {

  private final Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper;

  public GeofenceEventsReader(
      Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {
    this.geofenceEventRealmMapper = geofenceEventRealmMapper;
  }

  public boolean isGeofenceEventStored(Realm realm, OrchextraGeofence geofence) {
    boolean result = false;
    try {
      RealmResults<GeofenceEventRealm> realms = realm.where(GeofenceEventRealm.class)
          .equalTo(GeofenceEventRealm.CODE_FIELD_NAME, geofence.getCode())
          .equalTo(GeofenceEventRealm.TYPE_FIELD_NAME, geofence.getType().getStringValue())
          .findAll();
      result = (!realms.isEmpty());

      if (result) {
        GGGLogImpl.log("This geofence event was stored");
      } else {
        GGGLogImpl.log("This geofence event was not stored");
      }
    } catch (Exception e) {
      GGGLogImpl.log(e.getMessage(), LogLevel.ERROR);
    } finally {
      return result;
    }
  }

  public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(Realm realm,
      OrchextraGeofence orchextraGeofence) {

    RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
        .equalTo(GeofenceEventRealm.CODE_FIELD_NAME, orchextraGeofence.getCode())
        .findAll();

    if (results.size() > 1) {
      GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
    } else if (results.size() == 0) {

      GGGLogImpl.log("No geofence events found with code"
          + orchextraGeofence.getCode()
          + " and id "
          + orchextraGeofence.getGeofenceId());

      return new BusinessObject<>(null, BusinessError.createKoInstance("No geofence events found"));
    } else {
      GGGLogImpl.log("Retrieved geofence event found with code"
          + orchextraGeofence.getCode()
          + " and id "
          + orchextraGeofence.getGeofenceId());
    }

    return new BusinessObject<>(geofenceEventRealmMapper.externalClassToModel(results.first()),
        BusinessError.createOKInstance());
  }
}
