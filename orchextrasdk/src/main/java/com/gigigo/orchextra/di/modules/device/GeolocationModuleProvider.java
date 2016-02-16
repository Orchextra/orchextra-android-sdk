package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface GeolocationModuleProvider {
  GeolocationManager provideAndroidGeolocationManager();
  GeofencePendingIntentCreator provideGeofencePendingIntentCreator();
}
