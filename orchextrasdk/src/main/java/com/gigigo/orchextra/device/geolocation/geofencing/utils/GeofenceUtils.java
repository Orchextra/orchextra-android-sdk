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

package com.gigigo.orchextra.device.geolocation.geofencing.utils;

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
