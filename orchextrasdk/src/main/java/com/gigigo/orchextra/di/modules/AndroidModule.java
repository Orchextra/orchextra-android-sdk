package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.android.device.AndroidApp;
import com.gigigo.orchextra.android.device.AndroidDevice;
import com.gigigo.orchextra.android.googleClient.GoogleApiClientConnector;
import com.gigigo.orchextra.android.location.AndroidGeocoder;
import com.gigigo.orchextra.android.location.AndroidGeolocationManager;
import com.gigigo.orchextra.android.location.RetrieveLastKnownLocation;
import com.gigigo.orchextra.android.mapper.LocationMapper;
import com.gigigo.orchextra.android.permissions.PermissionLocationImp;
import com.gigigo.orchextra.android.proximity.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.android.proximity.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.android.proximity.geofencing.mapper.AndroidGeofenceMapper;
import com.gigigo.orchextra.android.proximity.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import dagger.Module;
import dagger.Provides;

@Module(includes = AndroidMapperModule.class)
public class AndroidModule {

    @Provides
    PermissionChecker providePermissionChecker(ContextProvider contextProvider) {
        return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
    }

    @PerDelegate
    @Provides
    PermissionLocationImp providePermissionLocationImp() {
        return new PermissionLocationImp();
    }

    @PerDelegate
    @Provides
    GoogleApiClientConnector provideGoogleApiClientConnector(ContextProvider contextProvider,
                                                             PermissionChecker permissionChecker,
                                                             PermissionLocationImp permissionLocationImp) {
        return new GoogleApiClientConnector(contextProvider, permissionChecker, permissionLocationImp);
    }

    @PerDelegate
    @Provides
    AndroidApp provideAndroidApp() {
        return new AndroidApp();
    }

    @PerDelegate
    @Provides
    AndroidDevice provideAndroidDevice(Context context) {
        return new AndroidDevice(context);
    }


    @PerDelegate
    @Provides
    RetrieveLastKnownLocation provideRetrieveLastKnownLocation(ContextProvider contextProvider,
                                                               GoogleApiClientConnector googleApiClientConnector,
                                                               PermissionChecker permissionChecker,
                                                               PermissionLocationImp permissionLocationImp) {
        return new RetrieveLastKnownLocation(contextProvider, googleApiClientConnector, permissionChecker, permissionLocationImp);
    }

    @PerDelegate
    @Provides
    AndroidGeocoder provideAndroidGeocoder(Context context) {
        return new AndroidGeocoder(context);
    }

    @PerDelegate
    @Provides
    AndroidGeolocationManager provideAndroidGeolocationManager(RetrieveLastKnownLocation retrieveLastKnownLocation,
                                                               AndroidGeocoder androidGeocoder) {
        return new AndroidGeolocationManager(retrieveLastKnownLocation, androidGeocoder);
    }

    @PerDelegate
    @Provides
    GeofencePendingIntentCreator provideGeofencePendingIntentCreator(Context context,
                                                                     PermissionChecker permissionChecker,
                                                                     PermissionLocationImp permissionLocationImp) {
        return new GeofencePendingIntentCreator(context);
    }

    @PerDelegate
    @Provides
    GeofenceDeviceRegister provideGeofenceDeviceRegister(ContextProvider contextProvider,
                                                         GoogleApiClientConnector googleApiClientConnector,
                                                         GeofencePendingIntentCreator geofencePendingIntentCreator,
                                                         PermissionChecker permissionChecker,
                                                         PermissionLocationImp permissionLocationImp) {
        return new GeofenceDeviceRegister(contextProvider, googleApiClientConnector, geofencePendingIntentCreator,
                permissionChecker, permissionLocationImp);
    }

    @PerDelegate
    @Provides
    AndroidGeofenceManager provideAndroidGeofenceManager(AndroidGeofenceMapper androidGeofenceMapper,
                                                         GeofenceDeviceRegister geofenceDeviceRegister,
                                                         LocationMapper locationMapper) {
        return new AndroidGeofenceManager(androidGeofenceMapper, geofenceDeviceRegister, locationMapper);
    }


}
