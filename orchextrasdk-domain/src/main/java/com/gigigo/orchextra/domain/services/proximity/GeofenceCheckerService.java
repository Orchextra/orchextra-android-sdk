package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/2/16.
 */
public class GeofenceCheckerService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public GeofenceCheckerService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse obtainTriggerableGeofenceList(List<String> triggeringGeofenceIds,
      OrchextraPoint triggeringPoint){

    List<OrchextraGeofence> triggerableGeofences = new ArrayList<>();

    for (String triggeringGeofenceId : triggeringGeofenceIds) {
      BusinessObject<OrchextraGeofence> boGeofence = proximityLocalDataProvider.obtainGeofenceByCodeFromDatabase(
          triggeringGeofenceId);

      if (boGeofence.isSuccess()) {

        OrchextraGeofence geofence = boGeofence.getData();
        double distanceFromGeofenceInKm = triggeringPoint.getDistanceFromPointInKm(geofence.getPoint());
        geofence.setGeofenceId(triggeringGeofenceId);
        geofence.setDistanceToDeviceInKm(distanceFromGeofenceInKm);

        triggerableGeofences.add(geofence);
      } else {
        return new InteractorResponse<>(new RetrieveProximityItemError(boGeofence.getBusinessError()));
      }
    }

    return new InteractorResponse<>(triggerableGeofences);

  }
}
