package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.interactors.authentication.errors.SdkAuthError;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationInteractor implements Interactor<InteractorResponse<ClientAuthData>> {

  private final AuthenticationDataProvider authenticationDataProvider;
  private final DeviceDetailsProvider deviceDetailsProvider;
  private final Session session;

  private SdkAuthCredentials sdkAuthCredentials;
  private String crmId;

  public AuthenticationInteractor(AuthenticationDataProvider authenticationDataProvider,
      DeviceDetailsProvider deviceDetailsProvider, Session session) {
    this.authenticationDataProvider = authenticationDataProvider;
    this.deviceDetailsProvider = deviceDetailsProvider;
    this.session = session;
  }

  public void setSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    this.sdkAuthCredentials = sdkAuthCredentials;
  }

  public void setCrm(String crmId) {
    this.crmId = crmId;
  }

  @Override public InteractorResponse<ClientAuthData> call() {

    //separate this operation

    BusinessObject<SdkAuthData> sdk = authenticationDataProvider.authenticateSdk(sdkAuthCredentials);
    if (!sdk.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }

    Credentials clientAuthCredentials = new ClientAuthCredentials(sdk.getData(), deviceDetailsProvider, crmId);

    BusinessObject<ClientAuthData> user = authenticationDataProvider.authenticateUser(clientAuthCredentials, crmId);
    if (!user.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(user.getBusinessError()));
    }

    if (user.getData() != null) {
      session.setTokenString(user.getData().getValue());
    }

    return new InteractorResponse<>(user.getData());
  }
}
