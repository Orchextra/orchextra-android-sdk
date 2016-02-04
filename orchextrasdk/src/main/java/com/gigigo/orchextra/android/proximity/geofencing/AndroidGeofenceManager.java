package com.gigigo.orchextra.android.proximity.geofencing;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.android.mapper.LocationMapper;
import com.gigigo.orchextra.android.proximity.geofencing.mapper.AndroidGeofenceMapper;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
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

    public GeofencingEvent getGeofencingEvent(Intent intent) {
        return GeofencingEvent.fromIntent(intent);
    }

    public ControlPoint getTriggeringPoint(GeofencingEvent geofencingEvent) {
        Location triggeringLocation = geofencingEvent.getTriggeringLocation();
        ControlPoint point = locationMapper.controlToModel(triggeringLocation);
        return point;
    }

    public List<String> getTriggeringGeofenceIds(GeofencingEvent geofencingEvent) {
        List<String> triggerGeofenceIds = new ArrayList<>();

        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        for (Geofence triggeringGeofence : triggeringGeofences) {
            triggerGeofenceIds.add(triggeringGeofence.getRequestId());
        }
        return triggerGeofenceIds;
    }

    public GeoPointEventType getGeofenceTransition(GeofencingEvent geofencingEvent) {
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
        //TODO review this
        return null;
    }
}
