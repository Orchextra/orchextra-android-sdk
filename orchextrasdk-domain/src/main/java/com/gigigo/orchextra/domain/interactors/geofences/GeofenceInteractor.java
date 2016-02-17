package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveGeofenceItemError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.actions.EventAccessor;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.proximity.GeofenceCheckerService;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/2/16.
 */
public class GeofenceInteractor implements Interactor<InteractorResponse<List<BasicAction>>>,
    EventAccessor{

  private final TriggerActionsFacadeService triggerActionsFacadeService;
  private final GeofenceCheckerService geofenceCheckerService;

  private List<String> triggeringGeofenceIds;
  private OrchextraPoint triggeringPoint;
  private GeoPointEventType geofenceTransition;

  private List<OrchextraGeofence> geofenceList;

  public GeofenceInteractor(TriggerActionsFacadeService triggerActionsFacadeService,
      GeofenceCheckerService geofenceCheckerService) {

    this.triggerActionsFacadeService = triggerActionsFacadeService;
    this.geofenceCheckerService = geofenceCheckerService;

    triggerActionsFacadeService.setEventAccessor(this);
  }

  @Override public InteractorResponse<List<BasicAction>> call() throws Exception {

    InteractorResponse<List<OrchextraGeofence>> response =
            geofenceCheckerService.obtainCheckedGeofences(triggeringGeofenceIds, geofenceTransition);

    if (response.getResult().size() == 0) {
      return new InteractorResponse<>(new RetrieveGeofenceItemError(BusinessError.createKoInstance("")));
    }

    geofenceList = response.getResult();

    if (geofenceTransition == GeoPointEventType.EXIT) {
      for (OrchextraGeofence geofence : geofenceList) {
        triggerActionsFacadeService.deleteScheduledActionIfExists(geofence);
      }
    }

    return triggerActionsFacadeService.triggerActions(geofenceList, geofenceTransition);
  }

  @Override public void updateEventWithAction(String actionId) {

    //TODO implement when geofence event triggers a scheduled action
  }

  public void setGeofenceData(List<String> triggeringGeofenceIds, OrchextraPoint triggeringPoint,
      GeoPointEventType geofenceTransition) {
    this.triggeringGeofenceIds = triggeringGeofenceIds;
    this.triggeringPoint = triggeringPoint;
    this.geofenceTransition = geofenceTransition;

  }
}
