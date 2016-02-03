package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.domain.data.api.auth.AuthenticationHeaderProvider;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;
import com.gigigo.orchextra.domain.interactors.user.LogOnUserInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInfoInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataProviderModule.class)
public class InteractorsModule {

  @Provides @Singleton AuthenticationInteractor provideAuthenticationInteractor(
      AuthenticationDataProvider authenticationDataProvider, DeviceDetailsProvider deviceDetailsProvider,
      AuthenticationHeaderProvider authenticationHeaderProvider){
    return new AuthenticationInteractor(authenticationDataProvider, deviceDetailsProvider, authenticationHeaderProvider);
  }

  @Provides @Singleton SendConfigInteractor provideSendConfigInteractor(
      ConfigDataProvider configDataProvider,
      AuthenticationDataProvider authenticationDataProvider){
    return new SendConfigInteractor(configDataProvider, authenticationDataProvider);
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
      ActionsDataProvider actionsDataProvider){
    return new GetActionInteractor(actionsDataProvider);
  }

    @Provides @Singleton
    RetrieveGeofencesFromDatabaseInteractor provideRetrieveGeofencesFromDatabaseInteractor(GeofenceDataProvider geofenceDataProvider) {
        return new RetrieveGeofencesFromDatabaseInteractor(geofenceDataProvider);
    }

    @Provides
    @Singleton
    RetrieveGeofenceTriggerInteractor provideRetrieveGeofenceDistanceInteractor(GeofenceDataProvider geofenceDataProvider,
                                                                                DeviceRunningModeType deviceRunningModeType) {
        return new RetrieveGeofenceTriggerInteractor(geofenceDataProvider, deviceRunningModeType);
    }

}