package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeocoder;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.device.geolocation.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

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
  @Provides AndroidGeolocationManager provideAndroidGeolocationManager(RetrieveLastKnownLocation retrieveLastKnownLocation,
      AndroidGeocoder androidGeocoder) {
    return new AndroidGeolocationManager(retrieveLastKnownLocation, androidGeocoder);
  }

  @Singleton
  @Provides GeofencePendingIntentCreator provideGeofencePendingIntentCreator(ContextProvider contextProvider,
      PermissionChecker permissionChecker,
      PermissionLocationImp permissionLocationImp) {
    return new GeofencePendingIntentCreator(contextProvider.getApplicationContext());
  }

  @Singleton
  @Provides
  LocationMapper provideLocationMapper() {
    return new LocationMapper();
  }

}
