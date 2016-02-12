package com.gigigo.orchextra.device.geolocation.geofencing;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;


public class AndroidGeofenceRegister implements Observer {

    private final GeofenceDeviceRegister geofenceDeviceRegister;

    public AndroidGeofenceRegister(GeofenceDeviceRegister geofenceDeviceRegister,
                                   ConfigObservable configObservable) {
        this.geofenceDeviceRegister = geofenceDeviceRegister;

        configObservable.registerObserver(this);
    }

    public void registerGeofences(OrchextraGeofenceUpdates geofenceUpdates) {
        geofenceDeviceRegister.register(geofenceUpdates);
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
