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
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;
import com.gigigo.orchextra.domain.services.DomainService;
import com.gigigo.orchextra.domain.services.triggers.TriggerDomainService;

import java.util.Iterator;
import java.util.List;

/**
 * this class prenteds be a sample of facade pattern
 */
public class TriggerActionsFacadeDomainService implements DomainService {

  private final TriggerDomainService triggerDomainService;
  private final GetActionDomainService getActionDomainService;
  private final ScheduleActionDomainService scheduleActionDomainService;
  private EventAccessor eventAccessor;

  public TriggerActionsFacadeDomainService(TriggerDomainService triggerDomainService,
                                           GetActionDomainService getActionDomainService, ScheduleActionDomainService scheduleActionDomainService) {

    this.triggerDomainService = triggerDomainService;
    this.getActionDomainService = getActionDomainService;
    this.scheduleActionDomainService = scheduleActionDomainService;
  }

  public void setEventAccessor(EventAccessor eventAccessor) {
    this.eventAccessor = eventAccessor;
  }
  public InteractorResponse<List<BasicAction>> triggerActions(
      List<OrchextraBeacon> orchextraBeacons) {
    return triggerActions(triggerDomainService.getTrigger(orchextraBeacons));
  }

  public InteractorResponse<List<BasicAction>> triggerActions(OrchextraRegion detectedRegion) {
    return triggerActions(triggerDomainService.getTrigger(detectedRegion));
}

  public InteractorResponse<List<BasicAction>> triggerActions(List<OrchextraGeofence> geofences,
      GeoPointEventType geofenceTransition) {
    return triggerActions(triggerDomainService.getTrigger(geofences, geofenceTransition));
  }

  private InteractorResponse<List<BasicAction>> triggerActions(InteractorResponse response) {

    if (response.hasError()) {
      return response;
    }

    InteractorResponse<List<BasicAction>> actions = getActionDomainService.getActions((List<Trigger>) response.getResult());

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
        scheduleActionDomainService.schedulePendingAction(basicAction);
        eventAccessor.updateEventWithAction(basicAction);
        iter.remove();
      }
    }
  }

  public void deleteScheduledActionIfExists(ScheduledActionEvent event) {
    if (event.hasActionRelated()) {
      scheduleActionDomainService.cancelPendingActionWithId(event.getActionRelatedId(),
          event.relatedActionIsCancelable());
    }
  }

  public InteractorResponse<List<BasicAction>> triggerActions(ScannerResult scanner, OrchextraLocationPoint orchextraLocationPoint) {
    return triggerActions(triggerDomainService.getTrigger(scanner, orchextraLocationPoint));
  }
}
