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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class GeofencePendingIntentCreator {

    private final Context context;

    public GeofencePendingIntentCreator(Context context) {
        this.context = context;
    }

    public PendingIntent getGeofencingPendingIntent() {
        try {
            Intent intent = new Intent(context, GeofenceIntentService.class);
            return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (Exception e) {
            return null;
        }
    }
}
