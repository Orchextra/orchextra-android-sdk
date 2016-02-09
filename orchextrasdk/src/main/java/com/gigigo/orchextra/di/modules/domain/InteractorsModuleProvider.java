package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  AuthenticationInteractor provideauthenticationInteractor();
  SendConfigInteractor provideSendConfigInteractor();
}
