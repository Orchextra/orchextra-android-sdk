package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;
import com.gigigo.orchextra.domain.interactors.authentication.errors.SdkAuthError;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationInteractor implements Interactor<InteractorResponse<SdkAuthCredentials>> {

  private final AuthenticationRepository authenticationRepository;
  private SdkAuthCredentials sdkAuthCredentials;

  public AuthenticationInteractor(AuthenticationRepository authenticationRepository) {
    this.authenticationRepository = authenticationRepository;
  }

  public void setSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    this.sdkAuthCredentials = sdkAuthCredentials;
  }

  @Override public InteractorResponse<SdkAuthCredentials> call() {

    BusinessObject<Sdk> sdk = authenticationRepository.authenticateSdk(sdkAuthCredentials);
    if (!sdk.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(sdk.getBusinessError()));
    }

    BusinessObject<User> user = authenticationRepository.authenticateUser(sdk.getData());
    if (!user.isSuccess()){
      return new InteractorResponse<>(new SdkAuthError(user.getBusinessError()));
    }

    return new InteractorResponse<>(new SdkAuthCredentials(user.getData()));
  }

}
