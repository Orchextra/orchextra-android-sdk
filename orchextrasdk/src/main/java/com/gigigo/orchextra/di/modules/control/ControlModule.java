package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.modules.domain.DomainModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.qualifiers.ConfigInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GeofenceInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.MainThread;
import com.gigigo.orchextra.di.qualifiers.SaveUserInteractorExecution;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;

import com.gigigo.orchextra.domain.outputs.MainThreadSpec;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

@Module(includes = DomainModule.class)
public class ControlModule {

  @Provides @Singleton
  GeofenceController provideProximityItemController(InteractorInvoker interactorInvoker,
      @GeofenceInteractorExecution Provider<InteractorExecution> geofenceInteractorExecution,
      ActionDispatcher actionDispatcher, ErrorLogger errorLogger, @MainThread ThreadSpec mainThreadSpec) {

    return new GeofenceController(interactorInvoker, geofenceInteractorExecution, actionDispatcher,
        errorLogger, mainThreadSpec);
  }

  @Provides
  @Singleton ConfigObservable providesConfigObservable(){
    return new ConfigObservable();
  }

  @Provides
  @Singleton ConfigController provideConfigController(@MainThread ThreadSpec backThreadSpec,
      InteractorInvoker interactorInvoker,
      @ConfigInteractorExecution Provider<InteractorExecution> sendConfigInteractorProvider,
      ConfigObservable configObservable) {
    return new ConfigController(backThreadSpec, interactorInvoker, sendConfigInteractorProvider, configObservable);
  }

  @Provides @Singleton AuthenticationController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      @SaveUserInteractorExecution Provider<InteractorExecution> interactorExecutionProvider,
      @MainThread ThreadSpec backThreadSpec){

    return new AuthenticationController(interactorInvoker, interactorExecutionProvider, backThreadSpec);
  }

  @Singleton @Provides @MainThread ThreadSpec provideMainThread(){
    return new MainThreadSpec();
  }
  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }


}
