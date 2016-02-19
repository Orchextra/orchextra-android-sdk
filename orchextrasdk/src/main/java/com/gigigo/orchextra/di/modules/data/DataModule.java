package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.di.qualifiers.ActionQueryRequest;
import com.gigigo.orchextra.di.qualifiers.ActionsResponse;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigRequest;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.action.ActionsDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.auth.AuthenticationDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.config.ConfigDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.auth.SessionDBDataSourceImpl;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsReader;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconsDBDataSourceImpl;
import gigigo.com.orchextra.data.datasources.db.config.ConfigDBDataSourceImpl;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceDBDataSourceImp;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsReader;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsUpdater;
import gigigo.com.orchextra.data.datasources.device.DeviceDetailsProviderImpl;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {ApiModule.class, ApiMappersModule.class, DBModule.class})
public class DataModule {

  @Provides @Singleton AuthenticationDataSource provideAuthenticationDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @SdkDataResponseMapper ApiGenericResponseMapper sdkResponseMapper,
      @ClientDataResponseMapper ApiGenericResponseMapper clientResponseMapper){

    return new AuthenticationDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        sdkResponseMapper, clientResponseMapper);
  }

  @Provides @Singleton ConfigDataSource provideConfigDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @ConfigResponseMapper ApiGenericResponseMapper configResponseMapper,
      @ConfigRequest ModelToExternalClassMapper configRequest){

    return new ConfigDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        configResponseMapper, configRequest);
  }

  @Provides @Singleton ActionsDataSource provideActionsDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @ActionQueryRequest ModelToExternalClassMapper queryModelToExternalClassMapper,
      @ActionsResponse ApiGenericResponseMapper actionsResponseMapper){

    return new ActionsDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        queryModelToExternalClassMapper, actionsResponseMapper);
  }


  @Provides @Singleton SessionDBDataSource provideSessionDBDataSource(
      ContextProvider contextProvider, SessionUpdater sessionUpdater, SessionReader sessionReader,
      RealmDefaultInstance realmDefaultInstance){
    return new SessionDBDataSourceImpl(contextProvider.getApplicationContext(),
        sessionUpdater, sessionReader, realmDefaultInstance);
  }

  @Provides @Singleton ConfigDBDataSource provideConfigDBDataSource(ContextProvider contextProvider,
      ConfigInfoResultUpdater configInfoResultUpdater,
      ConfigInfoResultReader configInfoResultReader,
      RealmDefaultInstance realmDefaultInstance){
    return new ConfigDBDataSourceImpl(contextProvider.getApplicationContext(),
        configInfoResultUpdater, configInfoResultReader, realmDefaultInstance);
  }

  @Provides @Singleton BeaconsDBDataSource provideBeaconsDBDataSource(ContextProvider contextProvider,
      BeaconEventsUpdater beaconEventsUpdater,
      BeaconEventsReader beaconEventsReader,
      RealmDefaultInstance realmDefaultInstance){
    return new BeaconsDBDataSourceImpl(contextProvider.getApplicationContext(),
        beaconEventsUpdater, beaconEventsReader, realmDefaultInstance);
  }

  @Provides
  @Singleton
  GeofenceDBDataSource provideGeofenceDBDataSource(ContextProvider contextProvider,
                                                   GeofenceEventsUpdater geofenceEventsUpdater,
                                                   GeofenceEventsReader geofenceEventsReader,
                                                   RealmDefaultInstance realmDefaultInstance) {
    return new GeofenceDBDataSourceImp(contextProvider.getApplicationContext(),
            geofenceEventsReader, geofenceEventsUpdater, realmDefaultInstance);
  }

  @Provides @Singleton DeviceDetailsProvider provideDeviceDetailsProvider(ContextProvider contextProvider){
    return new DeviceDetailsProviderImpl(contextProvider.getApplicationContext());
  }

  @Provides
  @Singleton
  RealmDefaultInstance provideRealmDefaultInstance() {
      return new RealmDefaultInstance();
  }

}
