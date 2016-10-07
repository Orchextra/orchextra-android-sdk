/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.device.geolocation.geofencing.mapper;

import com.gigigo.orchextra.device.geolocation.geofencing.utils.ConstantsAndroidGeofence;
import com.gigigo.orchextra.device.geolocation.geofencing.utils.GeofenceUtils;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

public class AndroidGeofenceConverter {

    public GeofencingRequest convertGeofencesToGeofencingRequest(List<OrchextraGeofence> orchextraGeofenceList) {
        List<Geofence> androidGeofenceList = new ArrayList<>();

        int i = 0;
        while (i < orchextraGeofenceList.size() && i < ConstantsAndroidGeofence.MAX_NUM_GEOFENCES) {
            OrchextraGeofence orchextraGeofence = orchextraGeofenceList.get(i);

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
            androidGeofenceList.add(geofence);

            i++;
        }

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .addGeofences(androidGeofenceList)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL)
                .build();

        return geofencingRequest;
    }

    public List<String> getCodeList(List<OrchextraGeofence> geofencesList) {
        List<String> codeList = new ArrayList<>();
        for (OrchextraGeofence geofence : geofencesList) {
            codeList.add(geofence.getCode());
        }
        return codeList;
    }
}
