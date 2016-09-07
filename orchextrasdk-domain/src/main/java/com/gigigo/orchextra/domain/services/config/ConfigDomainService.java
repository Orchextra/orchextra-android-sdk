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

package com.gigigo.orchextra.domain.services.config;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.notificationpush.GcmInstanceIdRegister;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.config.ConfigRequest;
import com.gigigo.orchextra.domain.model.entities.SdkVersionAppInfo;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;
import com.gigigo.orchextra.domain.services.DomainService;
/**
 *
 */
public class ConfigDomainService implements DomainService {

  private final ConfigDataProvider configDataProvider;
  private final AuthenticationDataProvider authenticationDataProvider;
  private final ServiceErrorChecker serviceErrorChecker;
  private final GcmInstanceIdRegister gcmInstanceIdRegister;

  private final SdkVersionAppInfo sdkVersionAppInfo;
  private final ObtainGeoLocationTask obtainGeoLocationTask;

  public ConfigDomainService(ConfigDataProvider configDataProvider,
                             AuthenticationDataProvider authenticationDataProvider,
                             ServiceErrorChecker serviceErrorChecker, SdkVersionAppInfo sdkVersionAppInfo,
                             ObtainGeoLocationTask obtainGeoLocationTask,
                             GcmInstanceIdRegister gcmInstanceIdRegister) {

    this.configDataProvider = configDataProvider;
    this.authenticationDataProvider = authenticationDataProvider;
    this.serviceErrorChecker = serviceErrorChecker;


    this.sdkVersionAppInfo = sdkVersionAppInfo;
    this.obtainGeoLocationTask = obtainGeoLocationTask;
    this.gcmInstanceIdRegister = gcmInstanceIdRegister;

  }

  public InteractorResponse<OrchextraUpdates> refreshConfig() {
    CrmUser crmUser = getCrm();

    Device device = getDevice();

    GeoLocation geolocation = obtainGeoLocationTask.getGeolocation();

    NotificationPush notificationPush = gcmInstanceIdRegister.getGcmNotification();
    ConfigRequest configRequest = generateConfig(geolocation, device, crmUser, notificationPush);

    BusinessObject<OrchextraUpdates> boOrchextraUpdates = configDataProvider.sendConfigInfo(configRequest);

    if (boOrchextraUpdates.isSuccess()) {
      return new InteractorResponse<>(boOrchextraUpdates.getData());
    } else {
      return processError(boOrchextraUpdates.getBusinessError());
    }
  }

  private CrmUser getCrm() {
    BusinessObject<CrmUser> boCrm = authenticationDataProvider.retrieveCrm();
    if (boCrm.isSuccess()) {
      return boCrm.getData();
    } else {
      return null;
    }
  }

  private Device getDevice() {
    BusinessObject<Device> boDevice = authenticationDataProvider.retrieveDevice();
    if (boDevice.isSuccess()) {
      return boDevice.getData();
    } else {
      return null;
    }
  }

  private InteractorResponse<OrchextraUpdates> processError(BusinessError businessError) {
    InteractorResponse interactorResponse = serviceErrorChecker.checkErrors(businessError);
    if (interactorResponse.hasError()) {
      return interactorResponse;
    } else {
      return refreshConfig();
    }
  }

  private ConfigRequest generateConfig(GeoLocation geoLocation, Device device, CrmUser crmUser, NotificationPush notificationPush) {
    ConfigRequest configRequest = new ConfigRequest();
    configRequest.setSdkAppInfo(sdkVersionAppInfo);
    configRequest.setDevice(device);
    configRequest.setGeoLocation(geoLocation);
    configRequest.setCrmUser(crmUser);
    configRequest.setNotificationPush(notificationPush);

    return configRequest;
  }
}
