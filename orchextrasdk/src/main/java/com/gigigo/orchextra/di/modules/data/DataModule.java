/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.OrchextraStatusDBDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.device.information.AndroidInstanceIdProvider;
import com.gigigo.orchextra.di.qualifiers.ActionQueryRequest;
import com.gigigo.orchextra.di.qualifiers.ActionsResponse;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigRequest;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;

import gigigo.com.orchextra.data.datasources.api.action.ActionsDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.auth.AuthenticationDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.config.ConfigDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import gigigo.com.orchextra.data.datasources.api.stats.StatsDataSourceImp;
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
import gigigo.com.orchextra.data.datasources.db.status.OrchextraStatusDBDataSourceImpl;
import gigigo.com.orchextra.data.datasources.db.status.OrchextraStatusReader;
import gigigo.com.orchextra.data.datasources.db.status.OrchextraStatusUpdater;
import gigigo.com.orchextra.data.datasources.device.DeviceDetailsProviderImpl;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Provider;
import orchextra.javax.inject.Singleton;


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

  @Provides @Singleton StatsDataSourceImp provideStatsDataSource(OrchextraApiService orchextraApiService,
                                                                 Provider<ApiServiceExecutor> serviceExecutorProvider) {
      return new StatsDataSourceImp(orchextraApiService, serviceExecutorProvider);
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
      RealmDefaultInstance realmDefaultInstance,
      OrchextraLogger orchextraLogger) {
    return new BeaconsDBDataSourceImpl(contextProvider.getApplicationContext(),
        beaconEventsUpdater, beaconEventsReader, realmDefaultInstance, orchextraLogger);
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

  @Provides
  @Singleton OrchextraStatusDBDataSource provideOrchextraStatusDBDataSource(ContextProvider contextProvider,
      OrchextraStatusUpdater orchextraStatusUpdater,
      OrchextraStatusReader orchextraStatusReader,
      RealmDefaultInstance realmDefaultInstance,
      OrchextraLogger orchextraLogger) {
    return new OrchextraStatusDBDataSourceImpl(contextProvider.getApplicationContext(),
        orchextraStatusUpdater, orchextraStatusReader, realmDefaultInstance, orchextraLogger);
  }

  @Provides
  @Singleton
  AndroidInstanceIdProvider provideAndroidInstanceIdProvider() {
    return new AndroidInstanceIdProvider();
  }

  @Provides @Singleton DeviceDetailsProvider provideDeviceDetailsProvider(ContextProvider contextProvider,
                                                                          AndroidInstanceIdProvider androidInstanceIdProvider){

    String androidInstanceId = androidInstanceIdProvider.provideAndroidInstanceId(contextProvider.getApplicationContext());
    return new DeviceDetailsProviderImpl(contextProvider.getApplicationContext(), androidInstanceId);
  }

  @Provides
  @Singleton
  RealmDefaultInstance provideRealmDefaultInstance() {
      return new RealmDefaultInstance();
  }

}
