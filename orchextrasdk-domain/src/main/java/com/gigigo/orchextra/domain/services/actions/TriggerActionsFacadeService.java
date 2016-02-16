package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.ScheduledActionEvent;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.DomaninService;
import com.gigigo.orchextra.domain.services.triggers.TriggerService;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public class TriggerActionsFacadeService implements DomaninService {

  private final TriggerService triggerService;
  private final GetActionService getActionService;
  private final ScheduleActionService scheduleActionService;
  private EventAccessor eventAccessor;

  public TriggerActionsFacadeService(TriggerService triggerService, GetActionService getActionService,
      ScheduleActionService scheduleActionService) {

    this.triggerService = triggerService;
    this.getActionService = getActionService;
    this.scheduleActionService = scheduleActionService;
  }

  public InteractorResponse<List<BasicAction>> triggerActions(List<OrchextraBeacon> orchextraBeacons) {
    return triggerActions(triggerService.getTrigger(orchextraBeacons));
  }

  public InteractorResponse<List<BasicAction>> triggerActions(OrchextraRegion detectedRegion) {
    return triggerActions(triggerService.getTrigger(detectedRegion));
  }

  public InteractorResponse<List<BasicAction>> triggerActions(List<OrchextraGeofence> geofences,
      GeoPointEventType geofenceTransition) {
    return triggerActions(triggerService.getTrigger(geofences, geofenceTransition));
  }

  private InteractorResponse triggerActions(InteractorResponse response) {

    if (response.hasError()) {
      return response;
    }

    InteractorResponse actions = getActionService.getActions((List<Trigger>) response.getResult());

    if (!actions.hasError()){
      scheduleActions(actions);
    }

    return actions;

  }

  public void setEventAccessor(EventAccessor eventAccessor) {
    this.eventAccessor = eventAccessor;
  }

  private void scheduleActions(InteractorResponse actionsResponse) {
    List<BasicAction> actions = (List<BasicAction>)actionsResponse.getResult();
    for (BasicAction action:actions){
      if (action.isScheduled()){
        scheduleActionService.schedulePendingAction(action);
        eventAccessor.updateEventWithAction(action.getScheduledAction().getId());
      }
    }
  }

    public void deleteScheduledActionIfExists(ScheduledActionEvent event) {
      if (event.hasActionRelated()){
        scheduleActionService.cancelPendingActionWithId(event.getActionRelated(), false);
      }
    }

}
