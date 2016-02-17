package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.interactors.config.ConfigServiceErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;

import com.gigigo.orchextra.domain.services.actions.ActionServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class DomainServiceErrorCheckerModule {

  //TODO must be provider
  @ConfigErrorChecker @Provides @PerExecution ServiceErrorChecker provideConfigServiceErrorChecker(
      AuthenticationService authenticationService){
    return new ConfigServiceErrorChecker(authenticationService);
  }


  @ActionsErrorChecker @Provides @PerExecution ServiceErrorChecker provideActionServiceErrorChecker(
      AuthenticationService authenticationService){
    return new ActionServiceErrorChecker(authenticationService);
  }

}
