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

package com.gigigo.orchextra.device.geolocation.geofencing;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

        if (geofencingEvent == null){
            return Collections.emptyList();
        }

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
