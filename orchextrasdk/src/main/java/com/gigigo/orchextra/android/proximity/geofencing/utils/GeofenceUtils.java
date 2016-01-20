package com.gigigo.orchextra.android.proximity.geofencing.utils;

import com.google.android.gms.location.Geofence;

public class GeofenceUtils {

    public static int getRadius(int radius) {
        return radius > 0 ? radius : ConstantsAndroidGeofence.RADIUS_DEFAULT;
    }

    public static int getStayTimeDelayMs(int stayTime) {
        return stayTime != -1 ?
                (ConstantsAndroidGeofence.MILISECONDS_IN_ONE_SECOND * stayTime) :
                ConstantsAndroidGeofence.STAY_TIME_MS_DEFAULT;
    }

    public static int getTransitionTypes(boolean entry, boolean exit) {
        if (entry && exit) {
            return Geofence.GEOFENCE_TRANSITION_ENTER |
                    Geofence.GEOFENCE_TRANSITION_EXIT |
                    Geofence.GEOFENCE_TRANSITION_DWELL;
        } else if (entry) {
            return Geofence.GEOFENCE_TRANSITION_ENTER |
                    Geofence.GEOFENCE_TRANSITION_DWELL;
        } else if (exit) {
            return Geofence.GEOFENCE_TRANSITION_EXIT |
                    Geofence.GEOFENCE_TRANSITION_DWELL;
        } else {
            return Geofence.GEOFENCE_TRANSITION_DWELL;
        }
    }
}
