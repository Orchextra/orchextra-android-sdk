package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.background.BackgroundTasksManagerImpl;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/1/16.
 */
@Module
public class ServicesModule {

  @PerService @Provides BackgroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner){
    return new BackgroundTasksManagerImpl(beaconScanner);
  }

  @PerService
  @Provides
  AndroidGeofenceManager provideAndroidGeofenceManager(AndroidGeofenceConverter androidGeofenceMapper,
      GeofenceDeviceRegister geofenceDeviceRegister,
      LocationMapper locationMapper) {
    return new AndroidGeofenceManager(androidGeofenceMapper, geofenceDeviceRegister, locationMapper);
  }

  @PerService
  @Provides GeofenceDeviceRegister provideGeofenceDeviceRegister(ContextProvider contextProvider,
      GoogleApiClientConnector googleApiClientConnector,
      GeofencePendingIntentCreator geofencePendingIntentCreator,
      PermissionChecker permissionChecker,
      PermissionLocationImp permissionLocationImp) {
    return new GeofenceDeviceRegister(contextProvider, googleApiClientConnector, geofencePendingIntentCreator,
        permissionChecker, permissionLocationImp);
  }

  @PerService
  @Provides AndroidGeofenceConverter provideAndroidGeofenceMapper() {
    return new AndroidGeofenceConverter();
  }

}
