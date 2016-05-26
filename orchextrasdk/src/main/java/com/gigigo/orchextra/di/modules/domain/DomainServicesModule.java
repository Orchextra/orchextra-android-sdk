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

package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.notificationpush.GcmInstanceIdRegisterImp;
import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.di.qualifiers.CrmValidation;
import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.Validator;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notificationpush.GcmInstanceIdRegister;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ImageRecognitionLocalDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.GetActionService;
import com.gigigo.orchextra.domain.services.actions.ScheduleActionService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationServiceImpl;
import com.gigigo.orchextra.domain.services.config.ConfigService;
import com.gigigo.orchextra.domain.services.config.ObtainGeoLocationTask;
import com.gigigo.orchextra.domain.services.imagerecognition.GetImageRecognitionCredentialsService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerService;
import com.gigigo.orchextra.domain.services.proximity.FutureGeolocation;
import com.gigigo.orchextra.domain.services.proximity.GeofenceCheckerService;
import com.gigigo.orchextra.domain.services.proximity.ObtainGeofencesService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerService;
import com.gigigo.orchextra.domain.services.themes.ThemeService;
import com.gigigo.orchextra.domain.services.triggers.TriggerService;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;

@Module(includes = DomainServiceErrorCheckerModule.class)
public class DomainServicesModule {

  @Provides @PerExecution AuthenticationService provideAuthService(
      AuthenticationDataProvider authDataProvider,
      DeviceDetailsProvider deviceDetailsProvider, Session session,
      @CrmValidation  Validator validator){

    return new AuthenticationServiceImpl(authDataProvider,
        deviceDetailsProvider, session, validator);
  }

  @Provides @PerExecution BeaconCheckerService provideBeaconCheckerService(
      ProximityLocalDataProvider proximityLocalDataProvider,
      ConfigDataProvider configDataProvider){

    return new BeaconCheckerService(proximityLocalDataProvider, configDataProvider);
  }

  @Provides @PerExecution RegionCheckerService provideRegionCheckerService(
      ProximityLocalDataProvider proximityLocalDataProvider){

    return new RegionCheckerService(proximityLocalDataProvider);
  }

  @Provides @PerExecution EventUpdaterService provideEventUpdaterService(
      ProximityLocalDataProvider proximityLocalDataProvider){

    return new EventUpdaterService(proximityLocalDataProvider);
  }

  @Provides @PerExecution GetActionService provideGetActionService(
      ActionsDataProvider actionsDataProvider,
      @ActionsErrorChecker ServiceErrorChecker serviceErrorChecker){

    return new GetActionService(actionsDataProvider,serviceErrorChecker);
  }

  @Provides @PerExecution ScheduleActionService provideScheduleActionService(
      ActionsSchedulerController actionsSchedulerController){
    return new ScheduleActionService(actionsSchedulerController);
  }

  @Provides @PerExecution TriggerService provideTriggerService(AppRunningMode appRunningMode){
    return new TriggerService(appRunningMode);
  }

  @Provides @PerExecution TriggerActionsFacadeService provideTriggerActionsFacadeService(
      TriggerService triggerService, GetActionService getActionService,
      ScheduleActionService scheduleActionService){
    return new TriggerActionsFacadeService(triggerService, getActionService, scheduleActionService);
  }

  @Provides @PerExecution ConfigService provideConfigService(ConfigDataProvider configDataProvider,
      AuthenticationDataProvider authenticationDataProvider,
      @ConfigErrorChecker ServiceErrorChecker errorChecker, AndroidApp androidApp,
      AndroidDevice androidDevice, ObtainGeoLocationTask obtainGeoLocationTask,
      GcmInstanceIdRegister gcmInstanceIdRegister){
    return new ConfigService(configDataProvider, authenticationDataProvider, errorChecker,
        androidApp.getAndroidAppInfo(), androidDevice.getAndroidDeviceInfo(), obtainGeoLocationTask,
        gcmInstanceIdRegister);
  }

  @Provides @PerExecution ObtainRegionsService provideObtainRegionsService(
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new ObtainRegionsService(proximityLocalDataProvider);
  }

  @Provides @PerExecution ObtainGeofencesService provideObtainGeofencesService(
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new ObtainGeofencesService(proximityLocalDataProvider);
  }

  @Provides @PerExecution
  GcmInstanceIdRegister provideGcmInstanceIdRegister(ContextProvider contextProvider) {
    return new GcmInstanceIdRegisterImp(contextProvider.getApplicationContext());
  }

  @Provides @PerExecution GeofenceCheckerService provideGeofenceCheckerService(
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new GeofenceCheckerService(proximityLocalDataProvider);
  }

  @Provides @PerExecution FutureGeolocation provideFutureGeolocation(){
    return new FutureGeolocation();
  }

  @Provides @PerExecution ThemeService provideThemeService(ConfigDataProvider configDataProvider) {
    return new ThemeService(configDataProvider);
  }

  @Provides @PerExecution GetImageRecognitionCredentialsService
  provideGetImageRecognitionCredentialsService(ImageRecognitionLocalDataProvider
      imageRecognitionLocalDataProvider) {

    return new GetImageRecognitionCredentialsService(imageRecognitionLocalDataProvider);

  }

  @Provides @PerExecution ObtainGeoLocationTask provideObtainGeoLocationTask(FutureGeolocation futureGeolocation,
                                                 GeolocationManager geolocationManager) {
    return new ObtainGeoLocationTask(futureGeolocation, geolocationManager);
  }
}
