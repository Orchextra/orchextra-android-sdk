package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.orchextra.android.proximity.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.android.proximity.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.android.proximity.geofencing.mapper.AndroidGeofenceMapper;
import com.gigigo.orchextra.android.proximity.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import com.gigigo.orchextra.android.googleClient.GoogleApiClientConnector;
import com.gigigo.orchextra.android.mapper.LocationMapper;

import dagger.Module;
import dagger.Provides;

@Module(includes = ControlMapperModule.class)
public class ControlModule {

    @PerDelegate
    @Provides
    GoogleApiClientConnector provideGoogleApiClientConnector(Context context) {
        return new GoogleApiClientConnector(context);
    }

    @PerDelegate
    @Provides
    GeofencePendingIntentCreator provideGeofencePendingIntentCreator(Context context) {
        return new GeofencePendingIntentCreator(context);
    }

    @PerDelegate
    @Provides
    GeofenceDeviceRegister provideGeofenceDeviceRegister(Context context,
                                                         GoogleApiClientConnector googleApiClientConnector,
                                                         GeofencePendingIntentCreator geofencePendingIntentCreator) {
        return new GeofenceDeviceRegister(context, googleApiClientConnector, geofencePendingIntentCreator);
    }

    @PerDelegate
    @Provides
    AndroidGeofenceManager provideAndroidGeofenceManager(AndroidGeofenceMapper androidGeofenceMapper,
                                                         GeofenceDeviceRegister geofenceDeviceRegister,
                                                         LocationMapper locationMapper) {
        return new AndroidGeofenceManager(androidGeofenceMapper, geofenceDeviceRegister, locationMapper);
    }
}
