package com.gigigo.orchextra.android.proximity.geofencing.mapper;

import com.gigigo.orchextra.android.proximity.geofencing.utils.ConstantsAndroidGeofence;
import com.gigigo.orchextra.android.proximity.geofencing.utils.GeofenceUtils;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.mapper.MapperModelToControl;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceMapper implements MapperModelToControl<List<com.gigigo.orchextra.domain.entities.Geofence>,GeofencingRequest> {

    public GeofencingRequest modelToControl(List<ControlGeofence> geofencePointList) {
        List<Geofence> geofenceList = new ArrayList<>();

        int i = 0;
        while (i < geofencePointList.size() && i < ConstantsAndroidGeofence.MAX_NUM_GEOFENCES) {
            ControlGeofence controlGeofence = geofencePointList.get(i);

            Geofence geofence = new Geofence.Builder()
                    .setRequestId(controlGeofence.getCode())
                    .setCircularRegion(controlGeofence.getPoint().getLat(),
                            controlGeofence.getPoint().getLng(),
                            GeofenceUtils.getRadius(controlGeofence.getRadius()))
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setLoiteringDelay(GeofenceUtils.getStayTimeDelayMs(controlGeofence.getStayTime()))  // Required when we use the transition type of GEOFENCE_TRANSITION_DWELL
                    .setTransitionTypes(
                            GeofenceUtils.getTransitionTypes(controlGeofence.isNotifyOnEntry(), controlGeofence.isNotifyOnExit()))
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
