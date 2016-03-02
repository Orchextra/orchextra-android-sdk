package com.gigigo.orchextra.domain.services.triggers;

import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriggerService implements DomaninService {

  private final AppRunningMode appRunningMode;

  public TriggerService(AppRunningMode appRunningMode) {
    this.appRunningMode = appRunningMode;
  }

  public InteractorResponse getTrigger(OrchextraRegion orchextraRegion) {

    List<Trigger> triggers = Arrays.asList(
        Trigger.createBeaconRegionTrigger(appRunningMode.getRunningModeType(), orchextraRegion));

    return new InteractorResponse(triggers);
  }

  public InteractorResponse getTrigger(List<OrchextraBeacon> orchextraBeacons) {
    return createTriggersForBeacons(orchextraBeacons);
  }

  private InteractorResponse createTriggersForBeacons(List<OrchextraBeacon> orchextraBeacons) {

    List<Trigger> triggers = new ArrayList<>();

    for (OrchextraBeacon orchextraBeacon : orchextraBeacons) {
      Trigger trigger =
          Trigger.createBeaconTrigger(appRunningMode.getRunningModeType(), orchextraBeacon);
      triggers.add(trigger);
    }

    return new InteractorResponse(triggers);
  }

  public InteractorResponse getTrigger(List<OrchextraGeofence> geofences,
      GeoPointEventType geofenceTransition) {
    List<Trigger> triggers = new ArrayList<>();

    for (OrchextraGeofence orchextraGeofence : geofences) {

      Trigger trigger =
          Trigger.createGeofenceTrigger(orchextraGeofence.getCode(), orchextraGeofence.getPoint(),
              appRunningMode.getRunningModeType(), orchextraGeofence.getDistanceToDeviceInKm(),
              geofenceTransition);

      triggers.add(trigger);
    }

    return new InteractorResponse(triggers);
  }
}
