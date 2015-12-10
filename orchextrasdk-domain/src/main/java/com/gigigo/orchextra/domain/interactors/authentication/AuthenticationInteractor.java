package com.gigigo.orchextra.domain.interactors.authentication;

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

    InteractorResponse interactorResponse;

    Sdk sdk = autheticateSdk();
    interactorResponse = authenticateUser(sdk);

    return interactorResponse;
  }

  private Sdk autheticateSdk() {
    return authenticationRepository.authenticateSdk(sdkAuthCredentials);
  }

  private InteractorResponse authenticateUser(Sdk sdk) {

    if (sdk ==  null){
      return new InteractorResponse<>(new SdkAuthError());
    }

    User user = authenticationRepository.authenticateUser(sdk);

    if (user == null){
      return new InteractorResponse<>(new SdkAuthError());
    }

    return new InteractorResponse<>(user);
  }
}
