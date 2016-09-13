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
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndroidGeofenceIntentServiceHandler {

    private final LocationMapper locationMapper;

    public AndroidGeofenceIntentServiceHandler(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public Object getGeofencingEvent(Intent intent) {
        return null;
    }

    public OrchextraLocationPoint getTriggeringPoint(Object geofencingEvent) {
        Location triggeringLocation = new Location("FakeOxProvider");
        triggeringLocation.setLatitude(-3.6281036);
        triggeringLocation.setLongitude(40.4458426);

        return locationMapper.externalClassToModel(triggeringLocation);
    }

    public List<String> getTriggeringGeofenceIds(Object geofencingEvent) {
        List<String> triggerGeofenceIds = new ArrayList<>();
        return triggerGeofenceIds;
    }

    public GeoPointEventType getGeofenceTransition(Object geofencingEvent) {
        return GeoPointEventType.ENTER;
    }
}
