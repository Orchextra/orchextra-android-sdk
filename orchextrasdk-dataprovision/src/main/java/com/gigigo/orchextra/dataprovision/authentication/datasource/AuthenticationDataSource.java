package com.gigigo.orchextra.dataprovision.authentication.datasource;

import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public interface AuthenticationDataSource {

  Sdk authenticateSdk(SdkAuthCredentials sdkAuthCredentials);

  User authenticateUser(Sdk sdk);

}
