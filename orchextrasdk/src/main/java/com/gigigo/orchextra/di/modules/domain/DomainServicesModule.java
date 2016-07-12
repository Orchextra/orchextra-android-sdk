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
import com.gigigo.orchextra.device.information.AndroidSdkVersionAppInfo;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.notificationpush.GcmInstanceIdRegisterImpl;
import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.di.qualifiers.ConfigErrorChecker;
import com.gigigo.orchextra.di.qualifiers.CrmValidation;
import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.Validator;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notificationpush.GcmInstanceIdRegister;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ImageRecognitionLocalDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterDomainService;
import com.gigigo.orchextra.domain.services.actions.GetActionDomainService;
import com.gigigo.orchextra.domain.services.actions.ScheduleActionDomainService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeDomainService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationServiceImpl;
import com.gigigo.orchextra.domain.services.config.ConfigDomainService;
import com.gigigo.orchextra.domain.services.config.LocalStorageService;
import com.gigigo.orchextra.domain.services.config.ObtainGeoLocationTask;
import com.gigigo.orchextra.domain.services.geofences.ObtainGeofencesDomainService;
import com.gigigo.orchextra.domain.services.imagerecognition.GetImageRecognitionCredentialsService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerDomainService;
import com.gigigo.orchextra.domain.services.geofences.FutureGeolocation;
import com.gigigo.orchextra.domain.services.geofences.GeofenceCheckerDomainService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsDomainService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerDomainService;
import com.gigigo.orchextra.domain.services.triggers.TriggerDomainService;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;

@Module(includes = DomainServiceErrorCheckerModule.class)
public class DomainServicesModule {

    @Provides
    @PerExecution
    AuthenticationService provideAuthService(
            AuthenticationDataProvider authDataProvider,
            DeviceDetailsProvider deviceDetailsProvider, Session session,
            @CrmValidation Validator validator) {

        return new AuthenticationServiceImpl(authDataProvider,
                deviceDetailsProvider, session, validator);
    }

    @Provides
    @PerExecution
    BeaconCheckerDomainService provideBeaconCheckerService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider,
            ConfigDataProvider configDataProvider) {

        return new BeaconCheckerDomainService(proximityAndGeofencesLocalDataProvider, configDataProvider);
    }

    @Provides
    @PerExecution
    RegionCheckerDomainService provideRegionCheckerService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {

        return new RegionCheckerDomainService(proximityAndGeofencesLocalDataProvider);
    }

    @Provides
    @PerExecution
    EventUpdaterDomainService provideEventUpdaterService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {

        return new EventUpdaterDomainService(proximityAndGeofencesLocalDataProvider);
    }

    @Provides
    @PerExecution
    GetActionDomainService provideGetActionService(
            ActionsDataProvider actionsDataProvider,
            @ActionsErrorChecker ServiceErrorChecker serviceErrorChecker) {

        return new GetActionDomainService(actionsDataProvider, serviceErrorChecker);
    }

    @Provides
    @PerExecution
    ScheduleActionDomainService provideScheduleActionService(
            ActionsSchedulerController actionsSchedulerController) {
        return new ScheduleActionDomainService(actionsSchedulerController);
    }

    @Provides
    @PerExecution
    TriggerDomainService provideTriggerService(AppRunningMode appRunningMode) {
        return new TriggerDomainService(appRunningMode);
    }

    @Provides
    @PerExecution
    TriggerActionsFacadeDomainService provideTriggerActionsFacadeService(
            TriggerDomainService triggerDomainService, GetActionDomainService getActionDomainService,
            ScheduleActionDomainService scheduleActionDomainService) {
        return new TriggerActionsFacadeDomainService(triggerDomainService, getActionDomainService, scheduleActionDomainService);
    }

    @Provides
    @PerExecution
    ConfigDomainService provideConfigService(ConfigDataProvider configDataProvider,
                                             AuthenticationDataProvider authenticationDataProvider,
                                             @ConfigErrorChecker ServiceErrorChecker errorChecker, AndroidSdkVersionAppInfo androidSdkVersionAppInfo,
                                             AndroidDevice androidDevice, ObtainGeoLocationTask obtainGeoLocationTask,
                                             GcmInstanceIdRegister gcmInstanceIdRegister) {
        return new ConfigDomainService(configDataProvider, authenticationDataProvider, errorChecker,
                androidSdkVersionAppInfo.getAndroidAppInfo(), androidDevice.getAndroidDeviceInfo(), obtainGeoLocationTask,
                gcmInstanceIdRegister);
    }

    @Provides
    @PerExecution
    ObtainRegionsDomainService provideObtainRegionsService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {
        return new ObtainRegionsDomainService(proximityAndGeofencesLocalDataProvider);
    }

    @Provides
    @PerExecution
    ObtainGeofencesDomainService provideObtainGeofencesService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {
        return new ObtainGeofencesDomainService(proximityAndGeofencesLocalDataProvider);
    }

    @Provides
    @PerExecution
    GcmInstanceIdRegister provideGcmInstanceIdRegister(ContextProvider contextProvider,
                                                       OrchextraLogger orchextraLogger) {
        return new GcmInstanceIdRegisterImpl(contextProvider.getApplicationContext(), orchextraLogger);
    }

    @Provides
    @PerExecution
    GeofenceCheckerDomainService provideGeofenceCheckerService(
            ProximityAndGeofencesLocalDataProvider proximityAndGeofencesLocalDataProvider) {
        return new GeofenceCheckerDomainService(proximityAndGeofencesLocalDataProvider);
    }

    @Provides
    @PerExecution
    FutureGeolocation provideFutureGeolocation() {
        return new FutureGeolocation();
    }



    @Provides
    @PerExecution
    GetImageRecognitionCredentialsService
    provideGetImageRecognitionCredentialsService(ImageRecognitionLocalDataProvider
                                                         imageRecognitionLocalDataProvider) {

        return new GetImageRecognitionCredentialsService(imageRecognitionLocalDataProvider);

    }

    @Provides
    @PerExecution
    ObtainGeoLocationTask provideObtainGeoLocationTask(FutureGeolocation futureGeolocation,
                                                       GeolocationManager geolocationManager) {
        return new ObtainGeoLocationTask(futureGeolocation, geolocationManager);
    }

    @Provides
    @PerExecution
    LocalStorageService provideLocalStorageService(ConfigDataProvider configDataProvider) {
        return new LocalStorageService(configDataProvider);
    }
}
