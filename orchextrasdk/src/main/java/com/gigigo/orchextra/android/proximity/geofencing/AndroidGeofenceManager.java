package com.gigigo.orchextra.android.proximity.geofencing;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.android.mapper.LocationMapper;
import com.gigigo.orchextra.android.proximity.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;


public class AndroidGeofenceManager {

    private final AndroidGeofenceConverter androidGeofenceConverter;
    private final GeofenceDeviceRegister geofenceDeviceRegister;
    private final LocationMapper locationMapper;

    public AndroidGeofenceManager(AndroidGeofenceConverter androidGeofenceConverter, GeofenceDeviceRegister geofenceDeviceRegister,
                                  LocationMapper locationMapper) {
        this.androidGeofenceConverter = androidGeofenceConverter;
        this.geofenceDeviceRegister = geofenceDeviceRegister;
        this.locationMapper = locationMapper;
    }

    public void registerGeofences(List<OrchextraGeofence> geofenceList) {
        GeofencingRequest geofencingRequest = androidGeofenceConverter.modelToControl(geofenceList);
        geofenceDeviceRegister.register(geofencingRequest);
    }

    public GeofencingEvent getGeofencingEvent(Intent intent) {
        return GeofencingEvent.fromIntent(intent);
    }

    public OrchextraPoint getTriggeringPoint(GeofencingEvent geofencingEvent) {
        Location triggeringLocation = geofencingEvent.getTriggeringLocation();
        return locationMapper.androidToModel(triggeringLocation);
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
