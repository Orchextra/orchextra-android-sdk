package com.gigigo.orchextra.modules.geofencing.mapper;

import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.mapper.MapperModelToControl;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceMapper implements MapperModelToControl<List<com.gigigo.orchextra.domain.entities.Geofence>,GeofencingRequest> {

    public GeofencingRequest modelToControl(List<ControlGeofence> geofencePointList) {
        List<Geofence> geofenceList = new ArrayList<>();

        for (ControlGeofence controlGeofence : geofencePointList) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(controlGeofence.getCode()) // The coordinates of the center of the geofence and the radius in meters.
                    .setCircularRegion(controlGeofence.getPoint().getLat(),
                            controlGeofence.getPoint().getLng(),
                            getRadius(controlGeofence.getRadius()))
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setLoiteringDelay(getStayTimeDelayMs(controlGeofence.getStayTime()))  // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
                    .setTransitionTypes(getTransitionTypes(controlGeofence.isNotifyOnEntry(), controlGeofence.isNotifyOnExit()))
                    .build();
            geofenceList.add(geofence);
        }

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder().addGeofences(geofenceList).build();

        return geofencingRequest;
    }

    private int getRadius(int radius) {
        return radius > 0 ? radius : 100;
    }

    private int getStayTimeDelayMs(int stayTime) {
        return stayTime != -1 ? 1000 * stayTime : 10000;
    }

    private int getTransitionTypes(boolean entry, boolean exit) {
        if (entry && exit) {
            return com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER |
                    com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT |
                    com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
        } else if (entry) {
            return com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER |
                    com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
        } else if (exit) {
            return com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT |
                    com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
        } else {
            return com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
        }
    }


}
