package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofenceIntentService;
import com.gigigo.orchextra.di.components.GeofenceIntentServiceComponent;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.di.components.ServiceComponent;
import com.gigigo.orchextra.di.components.TaskServiceComponent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public interface Injector {
  ServiceComponent injectServiceComponent (OrchextraBackgroundService myAppService);
  TaskServiceComponent injectTaskServiceComponent(OrchextraGcmTaskService orchextraGcmTaskService);
  GeofenceIntentServiceComponent injectGeofenceIntentServiceComponent(GeofenceIntentService geofenceIntentService);
}
