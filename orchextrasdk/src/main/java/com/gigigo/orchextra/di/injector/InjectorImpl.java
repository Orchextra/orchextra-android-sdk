package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofenceIntentService;
import com.gigigo.orchextra.di.components.DaggerGeofenceIntentServiceComponent;
import com.gigigo.orchextra.di.components.GeofenceIntentServiceComponent;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.di.components.DaggerServiceComponent;
import com.gigigo.orchextra.di.components.DaggerTaskServiceComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.components.ServiceComponent;
import com.gigigo.orchextra.di.components.TaskServiceComponent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class InjectorImpl implements Injector {

  private OrchextraComponent orchextraComponent;

  public InjectorImpl(OrchextraComponent orchextraComponent) {
    this.orchextraComponent = orchextraComponent;
  }

  @Override public ServiceComponent injectServiceComponent(OrchextraBackgroundService myAppService) {

    ServiceComponent serviceComponent = DaggerServiceComponent.builder().
        orchextraComponent(orchextraComponent).build();
    serviceComponent.injectOrchextraService(myAppService);

    return serviceComponent;
  }

  @Override public TaskServiceComponent injectTaskServiceComponent(OrchextraGcmTaskService orchextraGcmTaskService) {

    TaskServiceComponent taskServiceComponent = DaggerTaskServiceComponent.builder().
        orchextraComponent(orchextraComponent).build();
    taskServiceComponent.injectTaskService(orchextraGcmTaskService);

    return taskServiceComponent;
  }

  @Override public GeofenceIntentServiceComponent injectGeofenceIntentServiceComponent(
      GeofenceIntentService geofenceIntentService) {

    GeofenceIntentServiceComponent gisc = DaggerGeofenceIntentServiceComponent.builder().
        orchextraComponent(orchextraComponent).build();
    gisc.injectGeofenceIntentService(geofenceIntentService);

    return gisc;
  }

  public OrchextraComponent getOrchextraComponent() {
    return orchextraComponent;
  }
}
