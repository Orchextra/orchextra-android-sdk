package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface ServicesModuleProvider extends OrchextraModuleProvider {
  AndroidGeofenceManager provideAndroidGeofenceManager();
}
