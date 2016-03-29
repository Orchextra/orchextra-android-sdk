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
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.services.DomaninService;

public class ConfigService implements DomaninService {

  private final ConfigDataProvider configDataProvider;
  private final AuthenticationDataProvider authenticationDataProvider;
  private final ServiceErrorChecker serviceErrorChecker;

  private final App app;
  private final Device device;
  private final ObtainGeoLocationTask obtainGeoLocationTask;

  public ConfigService(ConfigDataProvider configDataProvider,
                       AuthenticationDataProvider authenticationDataProvider,
                       ServiceErrorChecker serviceErrorChecker, App app, Device device,
                       ObtainGeoLocationTask obtainGeoLocationTask) {

    this.configDataProvider = configDataProvider;
    this.authenticationDataProvider = authenticationDataProvider;
    this.serviceErrorChecker = serviceErrorChecker;


    this.device = device;
    this.app = app;
    this.obtainGeoLocationTask = obtainGeoLocationTask;
  }

  public InteractorResponse<OrchextraUpdates> refreshConfig() {
    Crm crm = getCrm();

    GeoLocation geolocation = obtainGeoLocationTask.getGeolocation();

    Config config = generateConfig(geolocation, crm);

    BusinessObject<OrchextraUpdates> boOrchextraUpdates = configDataProvider.sendConfigInfo(config);

    if (boOrchextraUpdates.isSuccess()) {
      return new InteractorResponse<>(boOrchextraUpdates.getData());
    } else {
      return processError(boOrchextraUpdates.getBusinessError());
    }
  }

  private Crm getCrm() {
    BusinessObject<Crm> boCrm = authenticationDataProvider.retrieveCrm();
    if (boCrm.isSuccess()) {
      return boCrm.getData();
    } else {
      return null;
    }
  }

  public InteractorResponse<OrchextraUpdates> processError(BusinessError businessError) {
    InteractorResponse interactorResponse = serviceErrorChecker.checkErrors(businessError);
    if (interactorResponse.hasError()) {
      return interactorResponse;
    } else {
      return refreshConfig();
    }
  }

  private Config generateConfig(GeoLocation geoLocation, Crm crm) {
    Config config = new Config();
    config.setApp(app);
    config.setDevice(device);
    config.setGeoLocation(geoLocation);
    config.setCrm(crm);

    return config;
  }
}
