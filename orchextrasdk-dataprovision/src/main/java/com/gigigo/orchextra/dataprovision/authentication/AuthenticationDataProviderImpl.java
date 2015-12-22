package com.gigigo.orchextra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
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
    //TODO check for exceptions

    if (!deviceToken.isSuccess() || deviceToken.getData().isExpired()){
      deviceToken = authenticationDataSource.authenticateSdk(credentials);
    }

    if (deviceToken.isSuccess()){
      sessionDBDataSource.saveSdkAuthResponse(deviceToken.getData());
    }

    return deviceToken;
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(Credentials credentials) {
    BusinessObject<ClientAuthData> sessionToken = sessionDBDataSource.getSessionToken();
    //TODO check for exceptions

    if (!sessionToken.isSuccess() || sessionToken.getData().isExpired()){
      //TODO check of crmID is available SHOULD exist DELETE user????
      sessionToken = authenticationDataSource.authenticateAnonymousUser(credentials);
    }

    if (sessionToken.isSuccess()){
      sessionDBDataSource.saveClientAuthResponse(sessionToken.getData());
    }

    return sessionToken;
  }
}
