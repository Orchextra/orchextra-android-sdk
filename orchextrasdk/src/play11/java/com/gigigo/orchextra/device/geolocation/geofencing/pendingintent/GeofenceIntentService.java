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

package com.gigigo.orchextra.device.geolocation.geofencing.pendingintent;

import android.app.IntentService;
import android.content.Intent;

import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceIntentServiceHandler;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceEventException;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import orchextra.javax.inject.Inject;

public class GeofenceIntentService extends IntentService {

    public static final String TAG = GeofenceIntentService.class.getSimpleName();

    @Inject
    AndroidGeofenceIntentServiceHandler geofenceHandler;

    @Inject
    GeofenceController controller;

    @Inject
    OrchextraLogger orchextraLogger;

    public GeofenceIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        InjectorImpl injector = OrchextraManager.getInjector();
        if (injector != null) {
            injector.injectGeofenceIntentServiceComponent(this);
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        processGeofenceIntentPending(intent);
    }

    public void processGeofenceIntentPending(Intent intent) {
        try {
            GeofencingEvent geofencingEvent = geofenceHandler.getGeofencingEvent(intent);

            List<String> geofenceIds = geofenceHandler.getTriggeringGeofenceIds(geofencingEvent);
            GeoPointEventType transition = geofenceHandler.getGeofenceTransition(geofencingEvent);
            orchextraLogger.log("Geofence Localizado: " + transition.getStringValue());
            if (geofenceIds != null && !geofenceIds.isEmpty()) {
                controller.processTriggers(geofenceIds, transition);
            }
        } catch (GeofenceEventException geofenceEventException) {
            orchextraLogger.log(geofenceEventException.getMessage(), OrchextraSDKLogLevel.ERROR);
        }
    }

}
