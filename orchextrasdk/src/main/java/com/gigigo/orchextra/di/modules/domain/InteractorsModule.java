package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.modules.data.DataProviderModule;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;

import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerService;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = {DomainServicesModule.class, DataProviderModule.class, InteractorErrorModule.class})
public class InteractorsModule {

  @Provides @Singleton SaveUserInteractor provideAuthenticationInteractor(
      AuthenticationService authenticationService, ConfigService configService){
    return new SaveUserInteractor(authenticationService, configService);
  }

  @Provides @Singleton SendConfigInteractor provideSendConfigInteractor(
      ConfigDataProvider configDataProvider, AuthenticationDataProvider authenticationDataProvider,
      @ConfigErrorChecker ServiceErrorChecker serviceErrorChecker,
      Session session){

    SendConfigInteractor sendConfigInteractor = new SendConfigInteractor(configDataProvider, authenticationDataProvider,
        serviceErrorChecker, session);

    return sendConfigInteractor;
  }

  @Provides @Singleton GetActionInteractor provideGetActionInteractor (
      ActionsDataProvider actionsDataProvider, ActionsSchedulerController actionsSchedulerController){
    return new GetActionInteractor(actionsDataProvider, actionsSchedulerController);
  }

  @Provides @Singleton RegionsProviderInteractor provideRegionsProviderInteractor (
      ObtainRegionsService obtainRegionsService){
    return new RegionsProviderInteractor(obtainRegionsService);
  }

  @Provides @Singleton BeaconEventsInteractor provideRegionCheckerInteractor (
      BeaconCheckerService beaconCheckerService, RegionCheckerService regionCheckerService,
      TriggerActionsFacadeService triggerActionsFacadeService,
      EventUpdaterService eventUpdaterService){

    return new BeaconEventsInteractor(beaconCheckerService, regionCheckerService,
        triggerActionsFacadeService, eventUpdaterService);
  }

    @Provides
    @Singleton
    RetrieveGeofenceTriggerInteractor provideRetrieveGeofenceDistanceInteractor(ProximityLocalDataProvider proximityLocalDataProvider,
                                                                                AppRunningMode appRunningMode) {
        return new RetrieveGeofenceTriggerInteractor(proximityLocalDataProvider, appRunningMode);
    }

}