package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.android.mapper.LocationMapper;
import com.gigigo.orchextra.android.proximity.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidMapperModule {

    @PerDelegate
    @Provides AndroidGeofenceConverter provideAndroidGeofenceMapper() {
        return new AndroidGeofenceConverter();
    }

    @PerDelegate
    @Provides
    LocationMapper provideLocationMapper() {
        return new LocationMapper();
    }

}
