package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.modules.domain.DomainModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

@Module(includes = DomainModule.class)
public class ControlModule {

  @Provides @Singleton ProximityItemController provideProximityItemController(
      @BackThread ThreadSpec backThreadSpec,
      InteractorInvoker interactorInvoker,
      GetActionInteractor getActionInteractor,
      RetrieveGeofenceTriggerInteractor retrieveGeofenceDistanceInteractor
  ) {
    return new ProximityItemController(backThreadSpec, interactorInvoker, getActionInteractor,
        retrieveGeofenceDistanceInteractor);
  }

  @Provides
  @Singleton ConfigObservable providesConfigObservable(){
    return new ConfigObservable();
  }

  @Provides
  @Singleton ConfigController provideConfigController(@BackThread ThreadSpec backThreadSpec,
      InteractorInvoker interactorInvoker,
      SendConfigInteractor sendConfigInteractor,
      ConfigObservable configObservable) {
    return new ConfigController(backThreadSpec, interactorInvoker, sendConfigInteractor, configObservable);
  }

  @Provides @Singleton AuthenticationController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      AuthenticationInteractor authenticationInteractor,
      @BackThread ThreadSpec backThreadSpec, Session session){

    return new AuthenticationController(interactorInvoker, authenticationInteractor, backThreadSpec, session);
  }

  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }


}
