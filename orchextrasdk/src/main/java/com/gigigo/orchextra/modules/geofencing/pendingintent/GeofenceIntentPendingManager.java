package com.gigigo.orchextra.modules.geofencing.pendingintent;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.utils.mapper.LocationMapper;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class GeofenceIntentPendingManager {

    private final LocationMapper locationMapper;

    public GeofenceIntentPendingManager(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public GeofencingEvent getGeofencingEvent(Intent intent) {
        return GeofencingEvent.fromIntent(intent);
    }

    public Location getTriggeringLocation(GeofencingEvent geofencingEvent) {
        return geofencingEvent.getTriggeringLocation();
    }

    public List<String> getTriggeringGeofenceIds(GeofencingEvent event) {
        List<String> triggerGeofenceIds = new ArrayList<>();

        List<Geofence> triggeringGeofences = event.getTriggeringGeofences();
        for (Geofence triggeringGeofence : triggeringGeofences) {
            triggerGeofenceIds.add(triggeringGeofence.getRequestId());
        }
        return triggerGeofenceIds;
    }

    public GeoPointEventType getGeofenceTransition(GeofencingEvent event) {
        if (!event.hasError()) {
            int transition = event.getGeofenceTransition();
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
