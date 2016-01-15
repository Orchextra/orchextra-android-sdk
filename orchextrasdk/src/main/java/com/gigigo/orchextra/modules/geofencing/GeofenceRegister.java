package com.gigigo.orchextra.modules.geofencing;

import com.gigigo.orchextra.modules.geofencing.mapper.AndroidGeofenceMapper;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.List;


public class GeofenceRegister {

    private final AndroidGeofenceMapper androidGeofenceMapper;
    private final GeofenceDeviceRegister geofenceDeviceRegister;

    public GeofenceRegister(AndroidGeofenceMapper androidGeofenceMapper, GeofenceDeviceRegister geofenceDeviceRegister) {
        this.androidGeofenceMapper = androidGeofenceMapper;
        this.geofenceDeviceRegister = geofenceDeviceRegister;
    }

    public void registerGeofences(List<Geofence> geofenceList) {
        GeofencingRequest geofencingRequest = androidGeofenceMapper.modelToDelegate(geofenceList);
        geofenceDeviceRegister.register(geofencingRequest);
    }
}
