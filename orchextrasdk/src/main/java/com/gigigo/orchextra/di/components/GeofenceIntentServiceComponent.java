package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofenceIntentService;
import com.gigigo.orchextra.di.modules.device.ServicesModuleProvider;
import com.gigigo.orchextra.di.modules.device.ServicesModule;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@PerService @Component(dependencies = OrchextraComponent.class, modules = ServicesModule.class)
public interface GeofenceIntentServiceComponent extends ServicesModuleProvider{
    void injectGeofenceIntentService(GeofenceIntentService geofenceIntentService);
}
