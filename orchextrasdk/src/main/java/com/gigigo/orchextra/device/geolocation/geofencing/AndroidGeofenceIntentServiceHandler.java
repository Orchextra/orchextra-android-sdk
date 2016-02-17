package com.gigigo.orchextra.device.geolocation.geofencing;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceIntentServiceHandler {

    private final LocationMapper locationMapper;

    public AndroidGeofenceIntentServiceHandler(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public GeofencingEvent getGeofencingEvent(Intent intent) {
        return GeofencingEvent.fromIntent(intent);
    }

    public OrchextraPoint getTriggeringPoint(GeofencingEvent geofencingEvent) {
        Location triggeringLocation = geofencingEvent.getTriggeringLocation();
        return locationMapper.externalClassToModel(triggeringLocation);
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
        throw new GeofenceEventException("Geofence Event Error was produced, code is: " +  geofencingEvent.getErrorCode() );
    }
}
