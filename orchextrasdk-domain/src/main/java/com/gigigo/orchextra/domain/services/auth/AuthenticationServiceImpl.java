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

package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
import com.gigigo.orchextra.domain.services.auth.errors.SdkAuthError;

public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationDataProvider authDataProvider;
  private final DeviceDetailsProvider deviceDetailsProvider;
  private final Session session;

  public AuthenticationServiceImpl(AuthenticationDataProvider authDataProvider,
      DeviceDetailsProvider deviceDetailsProvider, Session session) {

    this.authDataProvider = authDataProvider;
    this.deviceDetailsProvider = deviceDetailsProvider;
    this.session = session;
  }

  @Override public InteractorResponse authenticate() {
    BusinessObject<Crm> boCrm = authDataProvider.retrieveCrm();

    String crmId = null;
    if (boCrm.isSuccess()) {
      crmId = boCrm.getData().getCrmId();
    }
    return authenticate(crmId);
  }

  @Override public InteractorResponse authenticateUserWithCrmId(String crmId) {
    return authenticate(crmId);
  }

  @Override public BusinessObject<Crm> saveUser(Crm crm) {
    BusinessObject<Crm> boCrm = authDataProvider.retrieveCrm();

    if (boCrm.isSuccess() && !boCrm.getData().isEquals(crm.getCrmId())) {
      authDataProvider.clearAuthenticatedUser();
    }

    authDataProvider.storeCrmId(crm);

    return new BusinessObject<>(crm, BusinessError.createOKInstance());
  }

  private InteractorResponse authenticate(String crmId) {

    InteractorResponse<SdkAuthData> interactorResponse = authenticateSDK();

    if (!interactorResponse.hasError()) {
      Credentials credentials =
          new ClientAuthCredentials(interactorResponse.getResult(), deviceDetailsProvider, crmId);
      interactorResponse = authenticateClient(credentials, crmId);
    }
    return interactorResponse;
  }

  private InteractorResponse authenticateSDK() {

    Credentials credentials = new SdkAuthCredentials(session.getApiKey(), session.getApiSecret());
    BusinessObject<SdkAuthData> sdk = authDataProvider.authenticateSdk(credentials);

    if (!sdk.isSuccess()) {
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }
    return new InteractorResponse<>(sdk.getData());
  }

  private InteractorResponse authenticateClient(Credentials credentials, String crmId) {

    BusinessObject<ClientAuthData> user = authDataProvider.authenticateUser(credentials, crmId);

    if (!user.isSuccess()) {
      return new InteractorResponse<>(new AuthenticationError(user.getBusinessError()));
    }

    if (user.getData() != null) {
      session.setTokenString(user.getData().getValue());
    }

    return new InteractorResponse<>(user.getData());
  }
}
