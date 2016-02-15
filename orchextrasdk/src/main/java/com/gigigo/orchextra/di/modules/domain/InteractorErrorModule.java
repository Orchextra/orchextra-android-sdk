package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.ConfigServiceErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;

import com.gigigo.orchextra.domain.services.actions.ActionServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class InteractorErrorModule {

  //TODO must be provider
  @ConfigErrorChecker @Provides @Singleton ServiceErrorChecker provideConfigServiceErrorChecker(
      AuthenticationService authenticationService){
    return new ConfigServiceErrorChecker(authenticationService);
  }


  @ActionsErrorChecker @Provides @Singleton ServiceErrorChecker provideActionServiceErrorChecker(
      AuthenticationService authenticationService){
    return new ActionServiceErrorChecker(authenticationService);
  }

}
