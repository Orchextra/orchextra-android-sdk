package com.gigigo.orchextra.di.modules.android;

import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AndroidMapperModule {

    @Singleton
    @Provides AndroidGeofenceConverter provideAndroidGeofenceMapper() {
        return new AndroidGeofenceConverter();
    }

    @Singleton
    @Provides
    LocationMapper provideLocationMapper() {
        return new LocationMapper();
    }

}
