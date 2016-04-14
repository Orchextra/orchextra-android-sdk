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

import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofencesProviderInteractor;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;
import com.gigigo.orchextra.domain.interactors.themes.ObtainThemeInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigService;
import com.gigigo.orchextra.domain.services.config.ObtainGeoLocationTask;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerService;
import com.gigigo.orchextra.domain.services.proximity.GeofenceCheckerService;
import com.gigigo.orchextra.domain.services.proximity.ObtainGeofencesService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerService;
import com.gigigo.orchextra.domain.services.themes.ThemeService;

import dagger.Module;
import dagger.Provides;


@Module(includes = {DomainServicesModule.class})
public class InteractorsModule {

  @Provides @PerExecution SaveUserInteractor provideSaveUserInteractor(
      AuthenticationService authenticationService, ConfigService configService){
    return new SaveUserInteractor(authenticationService, configService);
  }

  @Provides @PerExecution SendConfigInteractor provideSendConfigInteractor(ConfigService configService){
    return new SendConfigInteractor(configService);
  }

  @Provides @PerExecution RegionsProviderInteractor provideRegionsProviderInteractor (
      ObtainRegionsService obtainRegionsService){
    return new RegionsProviderInteractor(obtainRegionsService);
  }

  @Provides @PerExecution BeaconEventsInteractor provideRegionCheckerInteractor (
      BeaconCheckerService beaconCheckerService, RegionCheckerService regionCheckerService,
      TriggerActionsFacadeService triggerActionsFacadeService,
      EventUpdaterService eventUpdaterService){

    return new BeaconEventsInteractor(beaconCheckerService, regionCheckerService,
        triggerActionsFacadeService, eventUpdaterService);
  }

    @Provides @PerExecution GeofenceInteractor provideGeofenceInteractor(
        TriggerActionsFacadeService triggerActionsFacadeService,
        GeofenceCheckerService geofenceCheckerService,
        EventUpdaterService eventUpdaterService) {

      return new GeofenceInteractor(triggerActionsFacadeService, geofenceCheckerService, eventUpdaterService);
    }

  @Provides @PerExecution GeofencesProviderInteractor provideGeofencesProviderInteractor(
      ObtainGeofencesService obtainGeofencesService) {
    return new GeofencesProviderInteractor(obtainGeofencesService);
  }

    @Provides @PerExecution ObtainThemeInteractor provideObtainThemeInteractor(ThemeService themeService){
        return new ObtainThemeInteractor(themeService);
    }

    @Provides @PerExecution ScannerInteractor provideScannerInteractor(TriggerActionsFacadeService triggerActionsFacadeService,
                                                                       ObtainGeoLocationTask obtainGeoLocationTask){
        return new ScannerInteractor(triggerActionsFacadeService, obtainGeoLocationTask);
    }
}