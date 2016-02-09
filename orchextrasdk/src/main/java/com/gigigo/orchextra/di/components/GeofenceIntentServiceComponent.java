package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofenceIntentService;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@PerService @Component(dependencies = OrchextraComponent.class)
public interface GeofenceIntentServiceComponent {
    void injectGeofenceIntentService(GeofenceIntentService geofenceIntentService);
}
