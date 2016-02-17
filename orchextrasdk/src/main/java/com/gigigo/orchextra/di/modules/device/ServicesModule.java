package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceIntentServiceHandler;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.di.scopes.PerService;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;
import com.gigigo.orchextra.domain.background.BackgroundTasksManagerImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/1/16.
 */
@Module
public class ServicesModule {

  @PerService @Provides BackgroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner,
                                                                             GeofenceRegister geofenceRegister){
    return new BackgroundTasksManagerImpl(beaconScanner, geofenceRegister);
  }

  @PerService
  @Provides
  LocationMapper provideLocationMapper() {
    return new LocationMapper();
  }

  @PerService @Provides
  AndroidGeofenceIntentServiceHandler provideAndroidGeofenceIntentServiceHandler(LocationMapper locationMapper) {
    return new AndroidGeofenceIntentServiceHandler(locationMapper);
  }

}
