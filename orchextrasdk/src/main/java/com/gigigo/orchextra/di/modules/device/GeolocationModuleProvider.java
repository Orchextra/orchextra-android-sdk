package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface GeolocationModuleProvider {
  AndroidGeolocationManager provideAndroidGeolocationManager();
  GeofencePendingIntentCreator provideGeofencePendingIntentCreator();
}
