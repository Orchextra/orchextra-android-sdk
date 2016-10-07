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

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.gggjavalib.general.utils.DateFormatConstants;
import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.ProximityItemType;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;


public class GeofenceExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiGeofence, OrchextraGeofence> {

  private final PointMapper pointMapper;

  public GeofenceExternalClassToModelMapper(PointMapper pointMapper) {
    this.pointMapper = pointMapper;
  }

  @Override public OrchextraGeofence externalClassToModel(ApiGeofence apiGeofence) {
    OrchextraGeofence geofence = new OrchextraGeofence();
    geofence.setRadius(apiGeofence.getRadius());
    geofence.setCode(apiGeofence.getCode());
    geofence.setName(apiGeofence.getName());
    geofence.setNotifyOnEntry(apiGeofence.getNotifyOnEntry());
    geofence.setNotifyOnExit(apiGeofence.getNotifyOnExit());
    geofence.setStayTime(apiGeofence.getStayTime());
    geofence.setTags(apiGeofence.getTags());
    geofence.setType(ProximityItemType.getProximityPointTypeValue(apiGeofence.getType()));

    geofence.setCreatedAt(DateUtils.stringToDateWithFormat(apiGeofence.getCreatedAt(),
        DateFormatConstants.DATE_FORMAT_TIME));
    geofence.setUpdatedAt(DateUtils.stringToDateWithFormat(apiGeofence.getUpdatedAt(),
        DateFormatConstants.DATE_FORMAT_TIME));

    geofence.setPoint(MapperUtils.checkNullDataResponse(pointMapper, apiGeofence.getPoint()));

    return geofence;
  }
}
