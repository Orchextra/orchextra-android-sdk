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
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

public class AuthenticationDataProviderImpl implements AuthenticationDataProvider {

  private final AuthenticationDataSource authenticationDataSource;
  private final SessionDBDataSource sessionDBDataSource;

  public AuthenticationDataProviderImpl(AuthenticationDataSource authenticationDataSource,
      SessionDBDataSource sessionDBDataSource) {
    this.authenticationDataSource = authenticationDataSource;
    this.sessionDBDataSource = sessionDBDataSource;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(Credentials credentials) {
    BusinessObject<SdkAuthData> deviceToken = sessionDBDataSource.getDeviceToken();

    if (!deviceToken.isSuccess() || deviceToken.getData().isExpired()) {
      deviceToken = authenticationDataSource.authenticateSdk(credentials);

      if (deviceToken.isSuccess()) {
        sessionDBDataSource.saveSdkAuthResponse(deviceToken.getData());
        SdkAuthCredentials sdkCredentials = ConsistencyUtils.checkInstance(credentials,
            SdkAuthCredentials.class);
        sessionDBDataSource.saveSdkAuthCredentials(sdkCredentials);
      }
    }

    return deviceToken;
  }

  @Override
  public BusinessObject<ClientAuthData> authenticateUser(Credentials credentials, String crmId) {
    BusinessObject<ClientAuthData> sessionToken = sessionDBDataSource.getSessionToken();

    if (!sessionToken.isSuccess() || sessionToken.getData() == null || sessionToken.getData()
        .isExpired()) {

      sessionToken = authenticationDataSource.authenticateUser(credentials);

      if (sessionToken.isSuccess()) {
        sessionDBDataSource.saveClientAuthResponse(sessionToken.getData());
        ClientAuthCredentials clientAuthCredentials = ConsistencyUtils.checkInstance(credentials,
            ClientAuthCredentials.class);
        sessionDBDataSource.saveClientAuthCredentials(clientAuthCredentials);
        saveCrmId(crmId);
      }
    }

    return sessionToken;
  }

  @Override public void clearAuthenticatedUser() {
    sessionDBDataSource.clearAuthenticatedUser();
  }

  private void saveCrmId(String crmId) {
    Crm crm = sessionDBDataSource.getCrm().getData();

    if (crm == null) {
      crm = new Crm();
    }

    crm.setCrmId(crmId);
    sessionDBDataSource.saveUser(crm);
  }

  @Override public BusinessObject<ClientAuthData> getCredentials() {
    return sessionDBDataSource.getSessionToken();
  }

  @Override public BusinessObject<Crm> retrieveCrm() {
    return sessionDBDataSource.getCrm();
  }

  @Override public boolean storeCrmId(Crm crm) {
    return sessionDBDataSource.storeCrm(crm);
  }
}
