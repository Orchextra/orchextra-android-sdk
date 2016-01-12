package com.gigigo.orchextra.delegates.proximity.manager;

import com.gigigo.orchextra.control.entities.GeofenceControl;

import java.util.List;

public class GeofenceRegister  {

    public GeofenceRegister() {

    }

    public void register(List<GeofenceControl> geofenceControlList) {
        for (GeofenceControl geofenceControl : geofenceControlList) {
            registerGeofence(geofenceControl);
        }
    }

    private void registerGeofence(GeofenceControl geofenceControl) {

    }
}
