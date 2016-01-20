package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.mapper.ControlGeofenceMapper;
import com.gigigo.orchextra.control.mapper.ControlPointMapper;
import com.gigigo.orchextra.control.mapper.ControlProximityPointTypeMapper;
import com.gigigo.orchextra.control.mapper.ListMapper;
import com.gigigo.orchextra.di.qualifiers.ControlGeofenceListMapper;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import com.gigigo.orchextra.domain.entities.Geofence;

import dagger.Module;
import dagger.Provides;

@Module
public class ControlMapperModule {

    @PerDelegate
    @Provides
    ControlPointMapper provideControlPointMapper() {
        return new ControlPointMapper();
    }

    @PerDelegate
    @Provides
    ControlProximityPointTypeMapper provideControlProximityPointTypeMapper() {
        return new ControlProximityPointTypeMapper();
    }

    @PerDelegate
    @Provides
    ControlGeofenceMapper provideControlGeofenceMapper(ControlPointMapper controlPointMapper,
                                                       ControlProximityPointTypeMapper controlProximityPointTypeMapper) {
        return new ControlGeofenceMapper(controlPointMapper, controlProximityPointTypeMapper);
    }

    @PerDelegate
    @Provides
    @ControlGeofenceListMapper
    ListMapper<Geofence, ControlGeofence> provideControlGeofenceListMapper(ControlPointMapper controlPointMapper,
                                                                       ControlProximityPointTypeMapper controlProximityPointTypeMapper) {
        ControlGeofenceMapper controlGeofenceMapper = new ControlGeofenceMapper(controlPointMapper, controlProximityPointTypeMapper);
        return new ListMapper<>(controlGeofenceMapper);
    }
}
