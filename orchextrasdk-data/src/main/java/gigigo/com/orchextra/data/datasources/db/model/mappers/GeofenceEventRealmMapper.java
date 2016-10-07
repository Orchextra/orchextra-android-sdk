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

package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.ProximityItemType;
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelatedWithRegionAndGeofences;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

public class GeofenceEventRealmMapper implements Mapper<OrchextraGeofence, GeofenceEventRealm> {

  private final Mapper<OrchextraLocationPoint, RealmPoint> realmPointMapper;

  public GeofenceEventRealmMapper(Mapper<OrchextraLocationPoint, RealmPoint> realmPointMapper) {
    this.realmPointMapper = realmPointMapper;
  }

  @Override public OrchextraGeofence externalClassToModel(GeofenceEventRealm geofenceEventRealm) {
    OrchextraGeofence geofence = new OrchextraGeofence();

    geofence.setRadius(geofenceEventRealm.getRadius());
    geofence.setPoint(
        MapperUtils.checkNullDataResponse(realmPointMapper, geofenceEventRealm.getPoint()));

    geofence.setCode(geofenceEventRealm.getCode());
    geofence.setNotifyOnEntry(geofenceEventRealm.isNotifyOnEntry());
    geofence.setNotifyOnExit(geofenceEventRealm.isNotifyOnExit());
    geofence.setStayTime(geofenceEventRealm.getStayTime());
    geofence.setType(ProximityItemType.getProximityPointTypeValue(geofenceEventRealm.getType()));

    geofence.setActionRelatedWithRegionAndGeofences(new ActionRelatedWithRegionAndGeofences(geofenceEventRealm.getActionRelated(),
        geofenceEventRealm.isActionRelatedCancelable()));

    return geofence;
  }

  @Override public GeofenceEventRealm modelToExternalClass(OrchextraGeofence geofence) {
    GeofenceEventRealm geofenceRealm = new GeofenceEventRealm();

    geofenceRealm.setRadius(geofence.getRadius());
    geofenceRealm.setPoint(MapperUtils.checkNullDataRequest(realmPointMapper, geofence.getPoint()));

    geofenceRealm.setCode(geofence.getCode());
    geofenceRealm.setNotifyOnEntry(geofence.isNotifyOnEntry());
    geofenceRealm.setNotifyOnExit(geofence.isNotifyOnExit());
    geofenceRealm.setStayTime(geofence.getStayTime());

    if (geofence.getType() != null) {
      geofenceRealm.setType(geofence.getType().getStringValue());
    }

    return geofenceRealm;
  }
}
