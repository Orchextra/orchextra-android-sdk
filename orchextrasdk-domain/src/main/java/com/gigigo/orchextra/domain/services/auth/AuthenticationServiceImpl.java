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
import com.gigigo.orchextra.domain.Validator;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
import com.gigigo.orchextra.domain.services.auth.errors.SdkAuthError;

public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationDataProvider authDataProvider;
  private final DeviceDetailsProvider deviceDetailsProvider;
  private final Validator<CrmUser> crmValidator;
  private final Session session;

  public AuthenticationServiceImpl(AuthenticationDataProvider authDataProvider,
      DeviceDetailsProvider deviceDetailsProvider, Session session, Validator crmValidator) {

    this.authDataProvider = authDataProvider;
    this.deviceDetailsProvider = deviceDetailsProvider;
    this.crmValidator = crmValidator;
    this.session = session;
  }

  @Override public InteractorResponse authenticate() {
    BusinessObject<CrmUser> boCrm = authDataProvider.retrieveCrm();

    String crmId = null;
    if (boCrm.isSuccess()) {
      crmId = boCrm.getData().getCrmId();
    }
    return authenticate(crmId);
  }

  @Override public InteractorResponse authenticateUserWithCrmId(String crmId) {
    return authenticate(crmId);
  }

  @Override public BusinessObject<CrmUser> saveUser(CrmUser crmUser) {
    BusinessObject<CrmUser> boCrm = authDataProvider.retrieveCrm();

    if (boCrm.isSuccess() && !boCrm.getData().isEquals(crmUser.getCrmId())) {
      authDataProvider.clearAuthenticatedUser();
    }

    crmValidator.doValidate(crmUser);

    authDataProvider.storeCrmId(crmUser);

    return new BusinessObject<>(crmUser, BusinessError.createOKInstance());
  }

  private InteractorResponse authenticate(String crmId) {

    InteractorResponse<SdkAuthData> interactorResponse = authenticateSDK();

    if (!interactorResponse.hasError()) {
      AuthCredentials authCredentials =
          new ClientAuthCredentials(interactorResponse.getResult(), deviceDetailsProvider, crmId);
      interactorResponse = authenticateClient(authCredentials, crmId);
    } else {
      System.out.println("ERROR step 2");
    }
    return interactorResponse;
  }

  private InteractorResponse authenticateSDK() {
    AuthCredentials authCredentials;
    if (session != null) {
      authCredentials = new SdkAuthCredentials(session.getApiKey(), session.getApiSecret());
    } else {
      authCredentials = new SdkAuthCredentials("", "");
    }

    BusinessObject<SdkAuthData> sdk = authDataProvider.authenticateSdk(authCredentials);

    if (!sdk.isSuccess()) {
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }
    return new InteractorResponse<>(sdk.getData());
  }

  private InteractorResponse authenticateClient(AuthCredentials authCredentials, String crmId) {

    BusinessObject<ClientAuthData> user = authDataProvider.authenticateUser(authCredentials, crmId);

    if (!user.isSuccess()) {
      return new InteractorResponse<>(new AuthenticationError(user.getBusinessError()));
    }

    if (user.getData() != null) {
      session.setTokenString(user.getData().getValue());
    }

    return new InteractorResponse<>(user.getData());
  }
}
