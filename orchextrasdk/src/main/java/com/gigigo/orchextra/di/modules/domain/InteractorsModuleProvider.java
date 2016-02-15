package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  SaveUserInteractor provideauthenticationInteractor();
  SendConfigInteractor provideSendConfigInteractor();
}
