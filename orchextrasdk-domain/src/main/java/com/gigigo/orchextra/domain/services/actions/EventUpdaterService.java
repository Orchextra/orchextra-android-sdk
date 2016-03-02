package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/2/16.
 */
public class EventUpdaterService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public EventUpdaterService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse associateActionToRegionEvent(OrchextraRegion orchextraRegion) {

    BusinessObject<OrchextraRegion> bo =
        proximityLocalDataProvider.updateRegionWithActionId(orchextraRegion);

    if (bo.isSuccess()) {
      return new InteractorResponse(bo.getData());
    } else {
      return new InteractorResponse(false);
    }
  }

  public InteractorResponse associateActionToGeofenceEvent(OrchextraGeofence geofence) {
    BusinessObject<OrchextraGeofence> bo =
        proximityLocalDataProvider.updateGeofenceWithActionId(geofence);

    if (bo.isSuccess()) {
      return new InteractorResponse(bo.getData());
    } else {
      return new InteractorResponse(false);
    }
  }
}