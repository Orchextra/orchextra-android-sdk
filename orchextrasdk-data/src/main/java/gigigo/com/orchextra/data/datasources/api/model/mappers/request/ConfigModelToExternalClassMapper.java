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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.config.ConfigRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiConfigRequest;


public class ConfigModelToExternalClassMapper
    implements ModelToExternalClassMapper<ConfigRequest, ApiConfigRequest> {

  private final AppModelToExternalClassMapper appRequestMapper;
  private final CrmModelToExternalClassMapper crmRequestMapper;
  private final DeviceModelToExternalClassMapper deviceRequestMapper;
  private final GeoLocationModelToExternalClassMapper geoLocationRequestMapper;
  private final PushNotificationModelToExternalClassMapper pushNotificationRequestMapper;

  public ConfigModelToExternalClassMapper(
      PushNotificationModelToExternalClassMapper pushNotificationRequestMapper,
      GeoLocationModelToExternalClassMapper geoLocationRequestMapper,
      DeviceModelToExternalClassMapper deviceRequestMapper,
      CrmModelToExternalClassMapper crmRequestMapper,
      AppModelToExternalClassMapper appRequestMapper) {
    this.pushNotificationRequestMapper = pushNotificationRequestMapper;
    this.geoLocationRequestMapper = geoLocationRequestMapper;
    this.deviceRequestMapper = deviceRequestMapper;
    this.crmRequestMapper = crmRequestMapper;
    this.appRequestMapper = appRequestMapper;
  }

  @Override public ApiConfigRequest modelToExternalClass(ConfigRequest config) {

    ApiConfigRequest configRequest = new ApiConfigRequest();

    configRequest.setApp(MapperUtils.checkNullDataRequest(appRequestMapper, config.getSdkAppInfo()));
    configRequest.setCrm(MapperUtils.checkNullDataRequest(crmRequestMapper, config.getCrmUser()));

    configRequest.setDevice(
        MapperUtils.checkNullDataRequest(deviceRequestMapper, config.getDevice()));

    configRequest.setGeoLocation(
        MapperUtils.checkNullDataRequest(geoLocationRequestMapper, config.getGeoLocation()));

    configRequest.setNotificationPush(
        MapperUtils.checkNullDataRequest(pushNotificationRequestMapper,
            config.getNotificationPush()));

    return configRequest;
  }
}
