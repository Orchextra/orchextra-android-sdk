package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeocoder;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceRegisterImp;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.geolocation.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;

import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class GeolocationModule {

  @Singleton
  @Provides
  RetrieveLastKnownLocation provideRetrieveLastKnownLocation(ContextProvider contextProvider,
      GoogleApiClientConnector googleApiClientConnector,
      PermissionChecker permissionChecker,
      PermissionLocationImp permissionLocationImp) {
    return new RetrieveLastKnownLocation(contextProvider, googleApiClientConnector, permissionChecker, permissionLocationImp);
  }

  @Singleton
  @Provides AndroidGeocoder provideAndroidGeocoder(ContextProvider contextProvider) {
    return new AndroidGeocoder(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides GeolocationManager provideAndroidGeolocationManager(
      RetrieveLastKnownLocation retrieveLastKnownLocation, AndroidGeocoder androidGeocoder) {
    return new AndroidGeolocationManager(retrieveLastKnownLocation, androidGeocoder);
  }

  @Singleton
  @Provides GeofencePendingIntentCreator provideGeofencePendingIntentCreator(ContextProvider contextProvider) {
    return new GeofencePendingIntentCreator(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides
  GeofenceRegister provideAndroidGeofenceManager(GeofenceDeviceRegister geofenceDeviceRegister,
                                                        ConfigObservable configObservable) {
    return new AndroidGeofenceRegisterImp(geofenceDeviceRegister, configObservable);
  }

  @Singleton
  @Provides AndroidGeofenceConverter provideAndroidGeofenceMapper() {
        return new AndroidGeofenceConverter();
    }

  @Singleton
  @Provides GeofenceDeviceRegister provideGeofenceDeviceRegister(ContextProvider contextProvider,
                                                                 GoogleApiClientConnector googleApiClientConnector,
                                                                 GeofencePendingIntentCreator geofencePendingIntentCreator,
                                                                 PermissionChecker permissionChecker,
                                                                 PermissionLocationImp permissionLocationImp,
                                                                 AndroidGeofenceConverter androidGeofenceConverter) {
    return new GeofenceDeviceRegister(contextProvider, googleApiClientConnector, geofencePendingIntentCreator,
            permissionChecker, permissionLocationImp, androidGeofenceConverter);
  }
}
