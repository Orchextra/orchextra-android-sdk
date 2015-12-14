package com.gigigo.orchextra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class AuthenticationRepositoryImpl implements AuthenticationRepository{

  //TODO Add Realm lib 

  private final AuthenticationDataSource authenticationDataSource;

  public AuthenticationRepositoryImpl(AuthenticationDataSource authenticationDataSource) {
    this.authenticationDataSource = authenticationDataSource;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(Credentials credentials) {
    return authenticationDataSource.authenticateSdk(credentials);
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(Credentials credentials) {
    //TODO (Store info in realm fot session Management)
    return authenticationDataSource.authenticateUser(credentials);
  }
}
