package com.gigigo.orchextra.domain.repository;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public interface AuthenticationRepository {

  BusinessObject<Sdk> authenticateSdk(SdkAuthCredentials sdkAuthCredentials);

  BusinessObject<User> authenticateUser(Sdk sdk);

}
