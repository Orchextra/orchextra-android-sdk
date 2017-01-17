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
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;
import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;
import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiAvailableCustomFieldType;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiCrmCustomFields;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiDeviceCustomFields;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiRegion;

public class ConfigApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiConfigData, ConfigurationInfoResult> {

  private static final int ONE_SECOND = 1000;

  private final GeofenceExternalClassToModelMapper geofenceResponseMapper;
  private final BeaconExternalClassToModelMapper beaconResponseMapper;

  private final VuforiaExternalClassToModelMapper vuforiaResponseMapper;
  private final AvailableCustomFieldExternalClassToModelMapper availableCustomFieldResponseMapper;
  private final CrmCustomFieldsExternalClassToModelMapper crmResponseMapper;
  private final DeviceCustomFieldsExternalClassToModelMapper deviceResponseMapper;

  public ConfigApiExternalClassToModelMapper(
      VuforiaExternalClassToModelMapper vuforiaResponseMapper,
      BeaconExternalClassToModelMapper beaconResponseMapper,
      GeofenceExternalClassToModelMapper geofenceResponseMapper,
      AvailableCustomFieldExternalClassToModelMapper availableCustomFieldResponseMapper,
      CrmCustomFieldsExternalClassToModelMapper crmResponseMapper,
      DeviceCustomFieldsExternalClassToModelMapper deviceResponseMapper) {
    this.vuforiaResponseMapper = vuforiaResponseMapper;
    this.beaconResponseMapper = beaconResponseMapper;
    this.geofenceResponseMapper = geofenceResponseMapper;
    this.availableCustomFieldResponseMapper = availableCustomFieldResponseMapper;
    this.crmResponseMapper = crmResponseMapper;
    this.deviceResponseMapper = deviceResponseMapper;
  }

  @Override public ConfigurationInfoResult externalClassToModel(ApiConfigData apiConfigData) {

    List<OrchextraRegion> beacons = mapBeacons(apiConfigData.getProximity());
    /*//asv hardcodding this is for testing behavior with eddystonebeacons and eddystoneregions
    //80EE872C-9229-4552-A0A9-02969FEEF0B8

             //PART 1
        List<OrchextraRegion> beaconsTruched = new ArrayList<>();
        for (int i = 0; i < beacons.size(); i++) {
            if (beacons.get(i).getMajor() == 666) {
                String code = beacons.get(i).getCode();
                beaconsTruched.add(new OrchextraRegion(code, "0xdee55ee72158449e9900", -1, -1, true));
            } else
                beaconsTruched.add(beacons.get(i));
        }
        */
    List<OrchextraGeofence> geofences = mapGeofences(apiConfigData.getGeoMarketing());
    List<AvailableCustomField> availableCustomFieldTypeList =
        mapAvailableCustomField(apiConfigData.getAvailableCustomFields());
    CrmCustomFields crmCustomFields = mapCrm(apiConfigData.getCrm());
    DeviceCustomFields deviceCustomFields = mapDevice(apiConfigData.getDevice());

    VuforiaCredentials vuforiaCredentials =
        MapperUtils.checkNullDataResponse(vuforiaResponseMapper, apiConfigData.getVuforia());
/*PART 2
        return new ConfigurationInfoResult.Builder(apiConfigData.getRequestWaitTime() * ONE_SECOND,
                geofences,
                beaconsTruched,
                vuforiaCredentials,
                availableCustomFieldTypeList,
                crmCustomFields,
                deviceCustomFields).build();
*/

    return new ConfigurationInfoResult.Builder(apiConfigData.getRequestWaitTime() * ONE_SECOND,
        geofences, beacons, vuforiaCredentials, availableCustomFieldTypeList, crmCustomFields,
        deviceCustomFields).build();
  }

  private List<OrchextraGeofence> mapGeofences(List<ApiGeofence> apiGeofences) {
    List<OrchextraGeofence> geofences = new ArrayList<>();

    if (apiGeofences != null) {
      for (ApiGeofence apiGeofence : apiGeofences) {
        geofences.add(MapperUtils.checkNullDataResponse(geofenceResponseMapper, apiGeofence));
      }
    }

    return geofences;
  }

  private List<OrchextraRegion> mapBeacons(List<ApiRegion> apiRegions) {
    List<OrchextraRegion> beacons = new ArrayList<>();

    if (apiRegions != null) {
      for (ApiRegion apiRegion : apiRegions) {
        beacons.add(MapperUtils.checkNullDataResponse(beaconResponseMapper, apiRegion));
      }
    }

    return beacons;
  }

  private List<AvailableCustomField> mapAvailableCustomField(
      Map<String, ApiAvailableCustomFieldType> apiAvailableCustomFields) {
    List<AvailableCustomField> availableCustomFieldList = new ArrayList<>();

    if (apiAvailableCustomFields != null) {
      availableCustomFieldList =
          MapperUtils.checkNullDataResponse(availableCustomFieldResponseMapper,
              apiAvailableCustomFields);
    }

    return availableCustomFieldList;
  }

  private CrmCustomFields mapCrm(ApiCrmCustomFields crm) {
    return MapperUtils.checkNullDataResponse(crmResponseMapper, crm);
  }

  private DeviceCustomFields mapDevice(ApiDeviceCustomFields device) {
    return MapperUtils.checkNullDataResponse(deviceResponseMapper, device);
  }
}
