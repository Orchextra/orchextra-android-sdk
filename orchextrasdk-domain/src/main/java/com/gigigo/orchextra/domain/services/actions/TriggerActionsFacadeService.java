/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.ScheduledActionEvent;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.DomaninService;
import com.gigigo.orchextra.domain.services.triggers.TriggerService;
import java.util.Iterator;
import java.util.List;

public class TriggerActionsFacadeService implements DomaninService {

  private final TriggerService triggerService;
  private final GetActionService getActionService;
  private final ScheduleActionService scheduleActionService;
  private EventAccessor eventAccessor;

  public TriggerActionsFacadeService(TriggerService triggerService,
      GetActionService getActionService, ScheduleActionService scheduleActionService) {

    this.triggerService = triggerService;
    this.getActionService = getActionService;
    this.scheduleActionService = scheduleActionService;
  }

  public void setEventAccessor(EventAccessor eventAccessor) {
    this.eventAccessor = eventAccessor;
  }
  public InteractorResponse<List<BasicAction>> triggerActions(
      List<OrchextraBeacon> orchextraBeacons) {
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

    if (!actions.hasError()) {
      scheduleActions(actions);
    }

    return actions;
  }



  private void scheduleActions(InteractorResponse actionsResponse) {
    List<BasicAction> actions = (List<BasicAction>) actionsResponse.getResult();

    for (Iterator<BasicAction> iter = actions.iterator(); iter.hasNext();) {
      BasicAction basicAction = iter.next();

      if (basicAction.isScheduled()) {
        scheduleActionService.schedulePendingAction(basicAction);
        eventAccessor.updateEventWithAction(basicAction);
        iter.remove();
      }
    }
  }

  public void deleteScheduledActionIfExists(ScheduledActionEvent event) {
    if (event.hasActionRelated()) {
      scheduleActionService.cancelPendingActionWithId(event.getActionRelatedId(),
          event.relatedActionIsCancelable());
    }
  }

  public InteractorResponse triggerActions(ScannerResult scanner, OrchextraPoint orchextraPoint) {
    return triggerActions(triggerService.getTrigger(scanner, orchextraPoint));
  }
}
