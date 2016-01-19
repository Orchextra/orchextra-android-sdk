package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.control.mapper.ControlPointMapper;
import com.gigigo.orchextra.control.mapper.ListMapper;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.qualifiers.ControlGeofenceListMapper;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceDistanceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = ControlModule.class)
public class DelegateModule {

  @Provides @PerDelegate AuthenticationController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      AuthenticationInteractor authenticationInteractor,
      @BackThread ThreadSpec backThreadSpec){

    return new AuthenticationController(interactorInvoker, authenticationInteractor, backThreadSpec);
  }

    @Provides @PerDelegate
    ProximityItemController provideProximityItemController(
            @BackThread ThreadSpec backThreadSpec,
            InteractorInvoker interactorInvoker,
            RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor,
            GetActionInteractor getActionInteractor,
            @ControlGeofenceListMapper ListMapper<Geofence, ControlGeofence> controlGeofenceListMapper,
            ControlPointMapper controlPointMapper,
            RetrieveGeofenceDistanceInteractor retrieveGeofenceDistanceInteractor
            ) {
        return new ProximityItemController(backThreadSpec, interactorInvoker, retrieveGeofencesInteractor,
                getActionInteractor, controlGeofenceListMapper, controlPointMapper, retrieveGeofenceDistanceInteractor);
    }

  @PerDelegate @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }

}
