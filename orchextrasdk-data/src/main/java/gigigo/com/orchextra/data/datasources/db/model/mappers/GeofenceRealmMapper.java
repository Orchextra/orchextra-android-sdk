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

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.model.ProximityPointType;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeofenceRealmMapper implements Mapper<OrchextraGeofence, GeofenceRealm> {

  private final Mapper<OrchextraPoint, RealmPoint> realmPointMapper;
  private final KeyWordRealmMapper keyWordRealmMapper;

  public GeofenceRealmMapper(Mapper realmPointMapper, KeyWordRealmMapper keyWordRealmMapper) {
    this.realmPointMapper = realmPointMapper;
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public GeofenceRealm modelToExternalClass(OrchextraGeofence geofence) {
    GeofenceRealm geofenceRealm = new GeofenceRealm();

    geofenceRealm.setRadius(geofence.getRadius());
    geofenceRealm.setPoint(MapperUtils.checkNullDataRequest(realmPointMapper, geofence.getPoint()));

    geofenceRealm.setCode(geofence.getCode());
    geofenceRealm.setId(geofence.getId());
    geofenceRealm.setName(geofence.getName());
    geofenceRealm.setNotifyOnEntry(geofence.isNotifyOnEntry());
    geofenceRealm.setNotifyOnExit(geofence.isNotifyOnExit());
    geofenceRealm.setStayTime(geofence.getStayTime());
    geofenceRealm.setTags(keyWordRealmMapper.stringKeyWordsToRealmList(geofence.getTags()));
    if (geofence.getType() != null) {
      geofenceRealm.setType(geofence.getType().getStringValue());
    }

    geofenceRealm.setCreatedAt(
        DateUtils.dateToStringWithFormat(geofence.getCreatedAt(), DateFormatConstants.DATE_FORMAT));

    geofenceRealm.setUpdatedAt(
        DateUtils.dateToStringWithFormat(geofence.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));

    return geofenceRealm;
  }

  @Override public OrchextraGeofence externalClassToModel(GeofenceRealm geofenceRealm) {
    OrchextraGeofence geofence = new OrchextraGeofence();

    geofence.setRadius(geofenceRealm.getRadius());
    geofence.setPoint(
        MapperUtils.checkNullDataResponse(realmPointMapper, geofenceRealm.getPoint()));

    geofence.setCode(geofenceRealm.getCode());
    geofence.setId(geofenceRealm.getId());
    geofence.setName(geofenceRealm.getName());
    geofence.setNotifyOnEntry(geofenceRealm.getNotifyOnEntry());
    geofence.setNotifyOnExit(geofenceRealm.getNotifyOnExit());
    geofence.setStayTime(geofenceRealm.getStayTime());
    geofence.setTags(keyWordRealmMapper.realmKeyWordsToStringList(geofenceRealm.getTags()));
    geofence.setType(ProximityPointType.getProximityPointTypeValue(geofenceRealm.getType()));

    geofence.setCreatedAt(DateUtils.stringToDateWithFormat(geofenceRealm.getCreatedAt(),
        DateFormatConstants.DATE_FORMAT));
    geofence.setUpdatedAt(DateUtils.stringToDateWithFormat(geofenceRealm.getUpdatedAt(),
        DateFormatConstants.DATE_FORMAT));

    return geofence;
  }
}
