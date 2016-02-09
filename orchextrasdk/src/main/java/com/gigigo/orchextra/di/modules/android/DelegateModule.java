package com.gigigo.orchextra.di.modules.android;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.modules.control.ControlModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = {ControlModule.class})
public class DelegateModule {

  @Provides @PerDelegate AuthenticationController provideAuthenticationController(
      InteractorInvoker interactorInvoker,
      AuthenticationInteractor authenticationInteractor,
      @BackThread ThreadSpec backThreadSpec){

    return new AuthenticationController(interactorInvoker, authenticationInteractor, backThreadSpec);
  }

    @Provides
    @PerDelegate
    ConfigController provideConfigController(@BackThread ThreadSpec backThreadSpec,
                                             InteractorInvoker interactorInvoker,
                                             SendConfigInteractor sendConfigInteractor,
                                              ConfigObservable configObservable) {
        return new ConfigController(backThreadSpec, interactorInvoker, sendConfigInteractor, configObservable);
    }


}
