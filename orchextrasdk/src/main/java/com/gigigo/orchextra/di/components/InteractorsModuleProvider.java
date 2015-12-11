package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  AuthenticationInteractor provideauthenticationInteractor();
}
