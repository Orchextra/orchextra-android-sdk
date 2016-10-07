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

package com.gigigo.orchextra.dataprovision.config;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.error.OrchextraBusinessErrors;
import com.gigigo.orchextra.domain.model.config.ConfigRequest;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;


public class ConfigDataProviderImpl implements ConfigDataProvider {

  private static final int DEFAULT_REQUEST_TIME = 100000;
  private final ConfigDataSource configDataSource;
  private final TriggersConfigurationDBDataSource triggersConfigurationDBDataSource;
  private final SessionDBDataSource sessionDBDataSource;
  private final Session session;

  public ConfigDataProviderImpl(ConfigDataSource configDataSource,
                                TriggersConfigurationDBDataSource triggersConfigurationDBDataSource, SessionDBDataSource sessionDBDataSource,
                                Session session) {

    this.configDataSource = configDataSource;
    this.triggersConfigurationDBDataSource = triggersConfigurationDBDataSource;
    this.sessionDBDataSource = sessionDBDataSource;
    this.session = session;
  }

  @Override public BusinessObject<OrchextraUpdates> sendConfigInfo(ConfigRequest configRequest) {

    boolean isAuthenticated = checkAuthenticationToken();
    if (!isAuthenticated) {
      return new BusinessObject<>(null,
          new BusinessError(OrchextraBusinessErrors.NO_AUTH_EXPIRED.getValue(), null,
              BusinessContentType.BUSINESS_ERROR_CONTENT));
    }

    BusinessObject<ConfigurationInfoResult> configResponse = configDataSource.sendConfigInfo(configRequest);

    if (configResponse.isSuccess()) {
      OrchextraUpdates orchextraUpdates =
          triggersConfigurationDBDataSource.saveConfigData(configResponse.getData());
      return new BusinessObject(orchextraUpdates, BusinessError.createOKInstance());
    } else {
      return new BusinessObject<>(null, configResponse.getBusinessError());
    }
  }

  @Override public int obtainRequestTime() {
    BusinessObject<ConfigurationInfoResult> bo = triggersConfigurationDBDataSource.obtainConfigData();
    if (bo.isSuccess()) {
      return bo.getData().getRequestWaitTime();
    } else {
      return DEFAULT_REQUEST_TIME;
    }
  }


  @Override
  public BusinessObject<Boolean> removeLocalStorage() {
    return triggersConfigurationDBDataSource.removeLocalStorage();
  }

  private boolean checkAuthenticationToken() {
    BusinessObject<SdkAuthData> deviceToken = sessionDBDataSource.getDeviceToken();
    if (deviceToken.isSuccess()) {
      BusinessObject<ClientAuthData> credentials = sessionDBDataSource.getSessionToken();

      if (credentials.isSuccess()) {
        session.setTokenString(credentials.getData().getValue());
        return true;
      }
    }
    return false;
  }
}
