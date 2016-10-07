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

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;


public class ConfigApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiConfigData, ConfigurationInfoResult> {

  private static final int ONE_SECOND = 1000;

  private final GeofenceExternalClassToModelMapper geofenceResponseMapper;
  private final BeaconExternalClassToModelMapper beaconResponseMapper;

  private final VuforiaExternalClassToModelMapper vuforiaResponseMapper;

  public ConfigApiExternalClassToModelMapper(
      VuforiaExternalClassToModelMapper vuforiaResponseMapper,
      BeaconExternalClassToModelMapper beaconResponseMapper,
      GeofenceExternalClassToModelMapper geofenceResponseMapper) {
    this.vuforiaResponseMapper = vuforiaResponseMapper;
    this.beaconResponseMapper = beaconResponseMapper;
    this.geofenceResponseMapper = geofenceResponseMapper;
  }

  @Override public ConfigurationInfoResult externalClassToModel(ApiConfigData apiConfigData) {

    List<OrchextraRegion> beacons = mapBeacons(apiConfigData.getProximity());
    List<OrchextraGeofence> geofences = mapGeofences(apiConfigData.getGeoMarketing());

    VuforiaCredentials vuforiaCredentials =
        MapperUtils.checkNullDataResponse(vuforiaResponseMapper, apiConfigData.getVuforia());

    return new ConfigurationInfoResult.Builder(apiConfigData.getRequestWaitTime() * ONE_SECOND, geofences,
        beacons, vuforiaCredentials).build();
  }

  private List<OrchextraGeofence> mapGeofences(List<ApiGeofence> apiGeofences) {
    List<OrchextraGeofence> geofences = new ArrayList<>();

    if (apiGeofences == null) return geofences;

    for (ApiGeofence apiGeofence : apiGeofences) {
      geofences.add(MapperUtils.checkNullDataResponse(geofenceResponseMapper, apiGeofence));
    }

    return geofences;
  }

  private List<OrchextraRegion> mapBeacons(List<ApiRegion> apiRegions) {
    List<OrchextraRegion> beacons = new ArrayList<>();

    if (beacons == null) return beacons;

    for (ApiRegion apiRegion : apiRegions) {
      beacons.add(MapperUtils.checkNullDataResponse(beaconResponseMapper, apiRegion));
    }

    return beacons;
  }
}
