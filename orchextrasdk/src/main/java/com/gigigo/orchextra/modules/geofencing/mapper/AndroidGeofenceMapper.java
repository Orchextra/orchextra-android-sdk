package com.gigigo.orchextra.modules.geofencing.mapper;

import com.gigigo.orchextra.utils.mapper.MapperModelToDelegate;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceMapper implements MapperModelToDelegate<List<com.gigigo.orchextra.domain.entities.Geofence>,GeofencingRequest> {

    public GeofencingRequest modelToDelegate(List<com.gigigo.orchextra.domain.entities.Geofence> geofencePointList) {
        List<Geofence> geofenceList = new ArrayList<>();

        for (com.gigigo.orchextra.domain.entities.Geofence geofencePoint : geofencePointList) {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(geofencePoint.getCode()) // The coordinates of the center of the geofence and the radius in meters.
                    .setCircularRegion(geofencePoint.getPoint().getLat(),
                            geofencePoint.getPoint().getLng(),
                            getRadius(geofencePoint.getRadius()))
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setLoiteringDelay(getStayTimeDelayMs(geofencePoint.getStayTime()))  // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
                    .setTransitionTypes(getTransitionTypes(geofencePoint.isNotifyOnEntry(), geofencePoint.isNotifyOnExit()))
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
