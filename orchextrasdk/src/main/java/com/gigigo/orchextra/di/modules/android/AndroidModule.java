package com.gigigo.orchextra.di.modules.android;

import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeocoder;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

@Module(includes = AndroidMapperModule.class)
public class AndroidModule {

    @Provides
    PermissionChecker providePermissionChecker(ContextProvider contextProvider) {
        return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
    }

    @Singleton
    @Provides
    PermissionLocationImp providePermissionLocationImp() {
        return new PermissionLocationImp();
    }

    @Singleton
    @Provides
    GoogleApiClientConnector provideGoogleApiClientConnector(ContextProvider contextProvider,
                                                             PermissionChecker permissionChecker,
                                                             PermissionLocationImp permissionLocationImp) {
        return new GoogleApiClientConnector(contextProvider, permissionChecker, permissionLocationImp);
    }

    @Singleton
    @Provides
    AndroidApp provideAndroidApp() {
        return new AndroidApp();
    }

    @Singleton
    @Provides
    AndroidDevice provideAndroidDevice(ContextProvider contextProvider) {
        return new AndroidDevice(contextProvider.getApplicationContext());
    }


    @Singleton
    @Provides
    RetrieveLastKnownLocation provideRetrieveLastKnownLocation(ContextProvider contextProvider,
                                                               GoogleApiClientConnector googleApiClientConnector,
                                                               PermissionChecker permissionChecker,
                                                               PermissionLocationImp permissionLocationImp) {
        return new RetrieveLastKnownLocation(contextProvider, googleApiClientConnector, permissionChecker, permissionLocationImp);
    }

    @Singleton
    @Provides
    AndroidGeocoder provideAndroidGeocoder(ContextProvider contextProvider) {
        return new AndroidGeocoder(contextProvider.getApplicationContext());
    }

    @Singleton
    @Provides
    AndroidGeolocationManager provideAndroidGeolocationManager(RetrieveLastKnownLocation retrieveLastKnownLocation,
                                                               AndroidGeocoder androidGeocoder) {
        return new AndroidGeolocationManager(retrieveLastKnownLocation, androidGeocoder);
    }

    @Singleton
    @Provides
    GeofencePendingIntentCreator provideGeofencePendingIntentCreator(ContextProvider contextProvider,
                                                                     PermissionChecker permissionChecker,
                                                                     PermissionLocationImp permissionLocationImp) {
        return new GeofencePendingIntentCreator(contextProvider.getApplicationContext());
    }

    @Singleton
    @Provides
    GeofenceDeviceRegister provideGeofenceDeviceRegister(ContextProvider contextProvider,
                                                         GoogleApiClientConnector googleApiClientConnector,
                                                         GeofencePendingIntentCreator geofencePendingIntentCreator,
                                                         PermissionChecker permissionChecker,
                                                         PermissionLocationImp permissionLocationImp) {
        return new GeofenceDeviceRegister(contextProvider, googleApiClientConnector, geofencePendingIntentCreator,
                permissionChecker, permissionLocationImp);
    }

  @Provides
  @Singleton ConfigObservable providesConfigObservable(){
    return new ConfigObservable();
  }

  @Singleton
  @Provides
  AndroidGeofenceManager provideAndroidGeofenceManager(AndroidGeofenceConverter androidGeofenceMapper,
      GeofenceDeviceRegister geofenceDeviceRegister,
      LocationMapper locationMapper) {
    return new AndroidGeofenceManager(androidGeofenceMapper, geofenceDeviceRegister, locationMapper);
  }

  @Provides @Singleton ProximityItemController provideProximityItemController(
      @BackThread ThreadSpec backThreadSpec,
      InteractorInvoker interactorInvoker,
      GetActionInteractor getActionInteractor,
      RetrieveGeofenceTriggerInteractor retrieveGeofenceDistanceInteractor
  ) {
    return new ProximityItemController(backThreadSpec, interactorInvoker, getActionInteractor,
        retrieveGeofenceDistanceInteractor);
  }


}
