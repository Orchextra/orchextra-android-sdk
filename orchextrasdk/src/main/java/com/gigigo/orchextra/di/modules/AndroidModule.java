package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.android.googleClient.GoogleApiClientConnector;
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
