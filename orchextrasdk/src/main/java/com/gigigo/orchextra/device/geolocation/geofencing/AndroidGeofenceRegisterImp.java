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
