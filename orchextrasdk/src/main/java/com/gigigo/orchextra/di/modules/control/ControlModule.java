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
import com.gigigo.orchextra.di.qualifiers.SaveUserInteractorExecution;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;

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
      ActionDispatcher actionDispatcher) {

    return new GeofenceController(interactorInvoker, geofenceInteractorExecution, actionDispatcher);
  }

  @Provides
  @Singleton ConfigObservable providesConfigObservable(){
    return new ConfigObservable();
  }

  @Provides
  @Singleton ConfigController provideConfigController(@BackThread ThreadSpec backThreadSpec,
      InteractorInvoker interactorInvoker,
      @ConfigInteractorExecution Provider<InteractorExecution> sendConfigInteractorProvider,
      ConfigObservable configObservable) {
    return new ConfigController(backThreadSpec, interactorInvoker, sendConfigInteractorProvider, configObservable);
  }

  @Provides @Singleton AuthenticationController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      @SaveUserInteractorExecution Provider<InteractorExecution> interactorExecutionProvider,
      @BackThread ThreadSpec backThreadSpec){

    return new AuthenticationController(interactorInvoker, interactorExecutionProvider, backThreadSpec);
  }

  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }


}
