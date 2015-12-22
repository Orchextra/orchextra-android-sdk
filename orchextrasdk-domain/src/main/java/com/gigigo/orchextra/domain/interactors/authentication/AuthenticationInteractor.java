package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.interactors.authentication.errors.SdkAuthError;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationInteractor implements Interactor<InteractorResponse<ClientAuthData>> {

  private final AuthenticationDataProvider authenticationDataProvider;
  private final DeviceDetailsProvider deviceDetatilsProvider;

  private SdkAuthCredentials sdkAuthCredentials;

  public AuthenticationInteractor(AuthenticationDataProvider authenticationDataProvider,
      DeviceDetailsProvider deviceDetatilsProvider) {
    this.authenticationDataProvider = authenticationDataProvider;
    this.deviceDetatilsProvider = deviceDetatilsProvider;
  }

  public void setSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    this.sdkAuthCredentials = sdkAuthCredentials;
  }

  @Override public InteractorResponse<ClientAuthData> call() {

    //separate this operation

    BusinessObject<SdkAuthData> sdk = authenticationDataProvider.authenticateSdk(sdkAuthCredentials);
    if (!sdk.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }

    Credentials clientAuthCredentials = new ClientAuthCredentials(sdk.getData(), deviceDetatilsProvider);

    BusinessObject<ClientAuthData> user = authenticationDataProvider.authenticateUser(clientAuthCredentials);
    if (!user.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(user.getBusinessError()));
    }

    return new InteractorResponse<>(user.getData());
  }

}
