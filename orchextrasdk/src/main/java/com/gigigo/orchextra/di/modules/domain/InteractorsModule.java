package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.modules.data.DataProviderModule;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.interactors.EventUpdaterInteractor;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconCheckerInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconTriggerInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.ObtainRegionsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionCheckerInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.error.InteractorErrorChecker;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.interactors.user.LogOnUserInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInfoInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = {DataProviderModule.class, InteractorErrorModule.class})
public class InteractorsModule {

  @Provides @Singleton AuthenticationInteractor provideAuthenticationInteractor(
      AuthenticationDataProvider authenticationDataProvider, DeviceDetailsProvider deviceDetailsProvider){
    return new AuthenticationInteractor(authenticationDataProvider, deviceDetailsProvider);
  }

  @Provides @Singleton SendConfigInteractor provideSendConfigInteractor(
      ConfigDataProvider configDataProvider,
      AuthenticationDataProvider authenticationDataProvider,
      @ConfigErrorChecker InteractorErrorChecker interactorErrorChecker){

    SendConfigInteractor sendConfigInteractor = new SendConfigInteractor(configDataProvider, authenticationDataProvider,
        interactorErrorChecker);

    interactorErrorChecker.setInteractor(sendConfigInteractor);

    return sendConfigInteractor;
  }

  @Provides @Singleton SaveUserInfoInteractor provideSaveUserInfoInteractor(
      ConfigDataProvider configDataProvider){
    return new SaveUserInfoInteractor(configDataProvider);
  }

  @Provides @Singleton LogOnUserInteractor provideLogOnUserInteractor(
      AuthenticationDataProvider authenticationDataProvider){
    return new LogOnUserInteractor(authenticationDataProvider);
  }

  @Provides @Singleton GetActionInteractor provideGetActionInteractor (
      ActionsDataProvider actionsDataProvider, ActionsSchedulerController actionsSchedulerController){
    return new GetActionInteractor(actionsDataProvider, actionsSchedulerController);
  }

  @Provides @Singleton ObtainRegionsInteractor provideObtainRegionsInteractor (
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new ObtainRegionsInteractor(proximityLocalDataProvider);
  }

  @Provides @Singleton RegionCheckerInteractor provideRegionCheckerInteractor (ProximityLocalDataProvider proximityLocalDataProvider){
    return new RegionCheckerInteractor(proximityLocalDataProvider);
  }

  @Provides @Singleton BeaconCheckerInteractor provideBeaconCheckerInteractor (
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new BeaconCheckerInteractor(proximityLocalDataProvider);
  }

  @Provides @Singleton EventUpdaterInteractor provideEventUpdaterInteractor (
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new EventUpdaterInteractor(proximityLocalDataProvider);
  }

  @Provides @Singleton BeaconTriggerInteractor provideBeaconTriggerInteractor (
      ProximityLocalDataProvider proximityLocalDataProvider, AppRunningMode appRunningMode){
    return new BeaconTriggerInteractor(proximityLocalDataProvider, appRunningMode);
  }

    @Provides
    @Singleton
    RetrieveGeofenceTriggerInteractor provideRetrieveGeofenceDistanceInteractor(ProximityLocalDataProvider proximityLocalDataProvider,
                                                                                AppRunningMode appRunningMode) {
        return new RetrieveGeofenceTriggerInteractor(proximityLocalDataProvider, appRunningMode);
    }

}