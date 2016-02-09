package com.gigigo.orchextra.device.geolocation.geofencing.mapper;

import com.gigigo.orchextra.device.geolocation.geofencing.utils.ConstantsAndroidGeofence;
import com.gigigo.orchextra.device.geolocation.geofencing.utils.GeofenceUtils;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceConverter {

    public GeofencingRequest modelToControl(List<OrchextraGeofence> geofencePointList) {
        List<Geofence> geofenceList = new ArrayList<>();

        int i = 0;
        while (i < geofencePointList.size() && i < ConstantsAndroidGeofence.MAX_NUM_GEOFENCES) {
            OrchextraGeofence orchextraGeofence = geofencePointList.get(i);

            Geofence geofence = new Geofence.Builder()
                    .setRequestId(orchextraGeofence.getCode())
                    .setCircularRegion(orchextraGeofence.getPoint().getLat(),
                        orchextraGeofence.getPoint().getLng(),
                            GeofenceUtils.getRadius(orchextraGeofence.getRadius()))
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setLoiteringDelay(GeofenceUtils.getStayTimeDelayMs(orchextraGeofence.getStayTime()))  // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
                    .setTransitionTypes(
                            GeofenceUtils.getTransitionTypes(orchextraGeofence.isNotifyOnEntry(), orchextraGeofence.isNotifyOnExit()))
                    .build();
            geofenceList.add(geofence);

            i++;
        }

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .addGeofences(geofenceList)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .build();

        return geofencingRequest;
    }
}
