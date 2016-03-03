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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofencePendingIntentCreator;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;


/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface GeolocationModuleProvider {
  GeolocationManager provideAndroidGeolocationManager();
  GeofencePendingIntentCreator provideGeofencePendingIntentCreator();
  GeofenceRegister provideAndroidGeofenceManager();
}
