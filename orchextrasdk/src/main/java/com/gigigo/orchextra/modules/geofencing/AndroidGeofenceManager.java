package com.gigigo.orchextra.modules.geofencing;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.modules.geofencing.mapper.AndroidGeofenceMapper;
import com.gigigo.orchextra.utils.mapper.LocationMapper;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;


public class AndroidGeofenceManager {

    private final AndroidGeofenceMapper androidGeofenceMapper;
    private final GeofenceDeviceRegister geofenceDeviceRegister;
    private final LocationMapper locationMapper;

    public AndroidGeofenceManager(AndroidGeofenceMapper androidGeofenceMapper, GeofenceDeviceRegister geofenceDeviceRegister,
                                  LocationMapper locationMapper) {
        this.androidGeofenceMapper = androidGeofenceMapper;
        this.geofenceDeviceRegister = geofenceDeviceRegister;
        this.locationMapper = locationMapper;
    }

    public void registerGeofences(List<ControlGeofence> geofenceList) {
        GeofencingRequest geofencingRequest = androidGeofenceMapper.modelToControl(geofenceList);
        geofenceDeviceRegister.register(geofencingRequest);
    }

    private GeofencingEvent getGeofencingEvent(Intent intent) {
        return GeofencingEvent.fromIntent(intent);
    }

    public ControlPoint getTriggeringPoint(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Location triggeringLocation = geofencingEvent.getTriggeringLocation();
        ControlPoint point = locationMapper.controlToModel(triggeringLocation);
        return point;
    }

    public List<String> getTriggeringGeofenceIds(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        List<String> triggerGeofenceIds = new ArrayList<>();

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        for (Geofence triggeringGeofence : triggeringGeofences) {
            triggerGeofenceIds.add(triggeringGeofence.getRequestId());
        }
        return triggerGeofenceIds;
    }

    public GeoPointEventType getGeofenceTransition(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (!geofencingEvent.hasError()) {
            int transition = geofencingEvent.getGeofenceTransition();
            switch(transition) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    return GeoPointEventType.ENTER;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    return GeoPointEventType.STAY;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    return GeoPointEventType.EXIT;
            }
        }
        return null;
    }
}
