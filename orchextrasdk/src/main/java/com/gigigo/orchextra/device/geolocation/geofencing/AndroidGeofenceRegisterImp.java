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

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofenceRegister;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;


public class AndroidGeofenceRegisterImp implements GeofenceRegister, Observer {

    private final GeofenceDeviceRegister geofenceDeviceRegister;
    private final ConfigObservable configObservable;

    private boolean isRegistered = false;

    public AndroidGeofenceRegisterImp(GeofenceDeviceRegister geofenceDeviceRegister,
                                      ConfigObservable configObservable) {
        this.geofenceDeviceRegister = geofenceDeviceRegister;
        this.configObservable = configObservable;
    }

    @Override
    public void registerGeofences(OrchextraGeofenceUpdates geofenceUpdates) {
        geofenceDeviceRegister.register(geofenceUpdates);
    }

    @Override
    public void startGeofenceRegister() {
        if (!isRegistered) {
            configObservable.registerObserver(this);
            isRegistered = true;
        }
    }

    @Override
    public void stopGeofenceRegister(){
        if (isRegistered) {
            configObservable.removeObserver(this);
            isRegistered = false;
        }
    }

    @Override
    public void update(OrchextraChanges observable, Object data) {
        OrchextraUpdates orchextraUpdates = (OrchextraUpdates) data;

        if (orchextraUpdates != null &&
                orchextraUpdates.getOrchextraGeofenceUpdates() != null) {
            OrchextraGeofenceUpdates orchextraGeofenceUpdates = orchextraUpdates.getOrchextraGeofenceUpdates();
            registerGeofences(orchextraGeofenceUpdates);
        }
    }
}
