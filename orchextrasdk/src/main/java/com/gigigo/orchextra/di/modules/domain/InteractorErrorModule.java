package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.AuthErrorHandler;
import com.gigigo.orchextra.domain.interactors.error.ConfigInteractorErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.InteractorErrorChecker;
import com.gigigo.orchextra.domain.interactors.error.PendingInteractorExecution;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class InteractorErrorModule {

  //TODO must be provider
  @ConfigErrorChecker @Provides @Singleton InteractorErrorChecker provideInteractorErrorChecker(
      AuthErrorHandler authErrorHandler, PendingInteractorExecution pendingInteractorExecution){
    return new ConfigInteractorErrorChecker(authErrorHandler, pendingInteractorExecution);
  }

  @Provides @Singleton PendingInteractorExecution providePendingInteractorExecution(){
    return new PendingInteractorExecution();
  }

  @Provides @Singleton AuthErrorHandler provideAuthErrorHandler(AuthenticationController authenticationController){
    return authenticationController;
  }
}
