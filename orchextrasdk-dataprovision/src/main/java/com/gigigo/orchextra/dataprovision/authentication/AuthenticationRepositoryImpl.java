package com.gigigo.orchextra.dataprovision.authentication;

import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class AuthenticationRepositoryImpl implements AuthenticationRepository{

  private final AuthenticationDataSource authenticationDataSource;

  public AuthenticationRepositoryImpl(AuthenticationDataSource authenticationDataSource) {
    this.authenticationDataSource = authenticationDataSource;
  }

  @Override public Sdk authenticateSdk(SdkAuthCredentials sdkAuthCredentials) {
    return authenticationDataSource.authenticateSdk(sdkAuthCredentials);
  }

  @Override public User authenticateUser(Sdk sdk) {
    return authenticationDataSource.authenticateUser(sdk);
  }
}
