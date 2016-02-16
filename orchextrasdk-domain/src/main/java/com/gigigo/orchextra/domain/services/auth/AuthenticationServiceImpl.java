package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.services.auth.errors.SdkAuthError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
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

  @Override
  public InteractorResponse authenticate(){
    BusinessObject<String> crmId = authDataProvider.getCrmID();
    return authenticate(crmId.getData());
  }

  @Override
  public InteractorResponse authenticateUserWithCrmId(String crmId){
    return authenticate(crmId);
  }

  private InteractorResponse authenticate(String crmId) {

    InteractorResponse<SdkAuthData> interactorResponse = authenticateSDK();

    if (!interactorResponse.hasError()){
      Credentials credentials = new ClientAuthCredentials(interactorResponse.getResult(), deviceDetailsProvider, crmId);
      interactorResponse = authenticateClient(credentials, crmId);
    }
    return interactorResponse;
  }

  private InteractorResponse authenticateSDK() {

    Credentials credentials = new SdkAuthCredentials(session.getApiKey(), session.getApiSecret());
    BusinessObject<SdkAuthData> sdk = authDataProvider.authenticateSdk(credentials);

    if (!sdk.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }
    return new InteractorResponse<>(sdk.getData());
  }

  public InteractorResponse authenticateClient(Credentials credentials, String crmId) {

    BusinessObject<ClientAuthData> user = authDataProvider.authenticateUser(credentials, crmId);

    if (!user.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(user.getBusinessError()));
    }

    if (user.getData() != null) {
      session.setTokenString(user.getData().getValue());
    }

    return new InteractorResponse<>(user.getData());
  }
}
