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

import com.gigigo.orchextra.di.scopes.PerExecutionOx;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusManager;
import com.gigigo.orchextra.domain.initalization.observables.ConfigChangeObservable;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.interactors.config.ClearLocalStorageInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceEventsInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofencesProviderInteractor;
import com.gigigo.orchextra.domain.interactors.imagerecognition.GetImageRecognitionCredentialsInteractor;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;

import com.gigigo.orchextra.domain.interactors.user.SaveCrmUserInteractor;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterDomainService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeDomainService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigDomainService;
import com.gigigo.orchextra.domain.services.config.LocalStorageService;
import com.gigigo.orchextra.domain.services.config.ObtainGeoLocationTask;
import com.gigigo.orchextra.domain.services.imagerecognition.GetImageRecognitionCredentialsService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerDomainService;
import com.gigigo.orchextra.domain.services.geofences.GeofenceCheckerDomainService;
import com.gigigo.orchextra.domain.services.geofences.ObtainGeofencesDomainService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsDomainService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerDomainService;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module(includes = {DomainServicesModule.class})
public class InteractorsModule {

    @Provides
    @PerExecutionOx
    SaveCrmUserInteractor provideSaveUserInteractor(
            AuthenticationService authenticationService,
            ConfigDomainService configDomainService,
            OrchextraStatusManager orchextraStatusManager) {
        return new SaveCrmUserInteractor(authenticationService, configDomainService, orchextraStatusManager);
    }

    @Provides
    @PerExecutionOx
    SendConfigInteractor provideSendConfigInteractor(ConfigDomainService configDomainService, ConfigChangeObservable configChangeObservable) {
        return new SendConfigInteractor(configDomainService, configChangeObservable);
    }

    @Provides
    @PerExecutionOx
    RegionsProviderInteractor provideRegionsProviderInteractor(
            ObtainRegionsDomainService obtainRegionsDomainService) {
        return new RegionsProviderInteractor(obtainRegionsDomainService);
    }

    @Provides
    @PerExecutionOx
    BeaconEventsInteractor provideRegionCheckerInteractor(
            BeaconCheckerDomainService beaconCheckerDomainService, RegionCheckerDomainService regionCheckerDomainService,
            TriggerActionsFacadeDomainService triggerActionsFacadeDomainService,
            EventUpdaterDomainService eventUpdaterDomainService) {

        return new BeaconEventsInteractor(beaconCheckerDomainService, regionCheckerDomainService,
                triggerActionsFacadeDomainService, eventUpdaterDomainService);
    }

    @Provides
    @PerExecutionOx
    GeofenceEventsInteractor provideGeofenceInteractor(
            TriggerActionsFacadeDomainService triggerActionsFacadeDomainService,
            GeofenceCheckerDomainService geofenceCheckerDomainService,
            EventUpdaterDomainService eventUpdaterDomainService) {

        return new GeofenceEventsInteractor(triggerActionsFacadeDomainService, geofenceCheckerDomainService, eventUpdaterDomainService);
    }

    @Provides
    @PerExecutionOx
    GeofencesProviderInteractor provideGeofencesProviderInteractor(
            ObtainGeofencesDomainService obtainGeofencesDomainService) {
        return new GeofencesProviderInteractor(obtainGeofencesDomainService);
    }


    @Provides
    @PerExecutionOx
    GetImageRecognitionCredentialsInteractor provideGetImageRecognitionCredentialsInteractor(GetImageRecognitionCredentialsService imageRecognitionCredentialsService) {
        return new GetImageRecognitionCredentialsInteractor(imageRecognitionCredentialsService);
    }

    @Provides
    @PerExecutionOx
    ScannerInteractor provideScannerInteractor(TriggerActionsFacadeDomainService triggerActionsFacadeDomainService,
                                               ObtainGeoLocationTask obtainGeoLocationTask) {
        return new ScannerInteractor(triggerActionsFacadeDomainService, obtainGeoLocationTask);
    }

    @Provides
    @PerExecutionOx
    ClearLocalStorageInteractor provideClearLocalStorageInteractor(LocalStorageService localStorageService) {
        return new ClearLocalStorageInteractor(localStorageService);
    }
}