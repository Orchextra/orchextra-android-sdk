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

package com.gigigo.orchextra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.vo.Device;

public class AuthenticationDataProviderImpl implements AuthenticationDataProvider {

  private final AuthenticationDataSource authenticationDataSource;
  private final SessionDBDataSource sessionDBDataSource;

  public AuthenticationDataProviderImpl(AuthenticationDataSource authenticationDataSource,
      SessionDBDataSource sessionDBDataSource) {
    this.authenticationDataSource = authenticationDataSource;
    this.sessionDBDataSource = sessionDBDataSource;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(AuthCredentials authCredentials) {
    BusinessObject<SdkAuthData> deviceToken = sessionDBDataSource.getDeviceToken();

    if (!deviceToken.isSuccess() || deviceToken.getData().isExpired()) {
      deviceToken = authenticationDataSource.authenticateSdk(authCredentials);

      if (deviceToken.isSuccess()) {
        sessionDBDataSource.saveSdkAuthResponse(deviceToken.getData());
        SdkAuthCredentials sdkCredentials =
            ConsistencyUtils.checkInstance(authCredentials, SdkAuthCredentials.class);
        sessionDBDataSource.saveSdkAuthCredentials(sdkCredentials);
      } else {
        System.out.println("ERROR  step1");
      }
    }

    return deviceToken;
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(AuthCredentials authCredentials,
      String crmId) {
    BusinessObject<ClientAuthData> sessionToken = sessionDBDataSource.getSessionToken();

    if (!sessionToken.isSuccess() || sessionToken.getData() == null || sessionToken.getData()
        .isExpired()) {

      sessionToken = authenticationDataSource.authenticateUser(authCredentials);

      if (sessionToken.isSuccess()) {
        sessionDBDataSource.saveClientAuthResponse(sessionToken.getData());
        ClientAuthCredentials clientAuthCredentials =
            ConsistencyUtils.checkInstance(authCredentials, ClientAuthCredentials.class);
        sessionDBDataSource.saveClientAuthCredentials(clientAuthCredentials);
        saveCrmId(crmId);
      }
    }

    return sessionToken;
  }

  @Override public void clearAuthenticatedSdk() {
    sessionDBDataSource.clearAuthenticatedSdk();
  }

  @Override public void clearAuthenticatedUser() {
    sessionDBDataSource.clearAuthenticatedUser();
  }

  private void saveCrmId(String crmId) {
    CrmUser crmUser = sessionDBDataSource.getCrm().getData();

    if (crmUser == null) {
      crmUser = new CrmUser();
    }

    crmUser.setCrmId(crmId);
    sessionDBDataSource.saveUser(crmUser);
  }

  @Override public BusinessObject<ClientAuthData> getCredentials() {
    return sessionDBDataSource.getSessionToken();
  }

  @Override public BusinessObject<CrmUser> retrieveCrm() {
    return sessionDBDataSource.getCrm();
  }

  @Override public boolean storeCrmId(CrmUser crmUser) {
    return sessionDBDataSource.storeCrm(crmUser);
  }

  @Override public BusinessObject<Device> retrieveDevice() {
    return sessionDBDataSource.retrieveDevice();
  }
}
