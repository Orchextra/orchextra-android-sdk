package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.modules.domain.DomainModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
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

  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }


}
