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

package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.ScheduledActionEvent;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelatedWithRegionAndGeofences;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.actions.EventAccessor;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterDomainService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeDomainService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerDomainService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerDomainService;

import java.util.List;

public class BeaconEventsInteractor
    implements Interactor<InteractorResponse<List<BasicAction>>>, EventAccessor {

  private final BeaconCheckerDomainService beaconCheckerDomainService;
  private final RegionCheckerDomainService regionCheckerDomainService;
  private final TriggerActionsFacadeDomainService triggerActionsFacadeDomainService;
  private final EventUpdaterDomainService eventUpdaterDomainService;

  private ProximityEventType eventType;
  private Object data;

  public BeaconEventsInteractor(BeaconCheckerDomainService beaconCheckerDomainService,
      RegionCheckerDomainService regionCheckerDomainService,
      TriggerActionsFacadeDomainService triggerActionsFacadeDomainService,
      EventUpdaterDomainService eventUpdaterDomainService) {

    this.beaconCheckerDomainService = beaconCheckerDomainService;
    this.regionCheckerDomainService = regionCheckerDomainService;
    this.triggerActionsFacadeDomainService = triggerActionsFacadeDomainService;
    this.eventUpdaterDomainService = eventUpdaterDomainService;

    triggerActionsFacadeDomainService.setEventAccessor(this);
  }

  @Override public InteractorResponse<List<BasicAction>> call() throws Exception {
    switch (eventType) {
      case BEACONS_DETECTED:
        return beaconsEventResult();
      case REGION_ENTER:
      case REGION_EXIT:
        return regionEventResult(eventType);
      default:
        return new InteractorResponse<>(
            new BeaconsInteractorError(BeaconBusinessErrorType.UNKNOWN_EVENT));
    }
  }

  public void setEventType(ProximityEventType eventType) {
    this.eventType = eventType;
  }
    //this store the beacon or the regiaon beacon
  public void setData(Object data) {
    this.data = data;
  }

  private InteractorResponse<List<BasicAction>> beaconsEventResult() {
    List<OrchextraBeacon> orchextraBeacons = (List<OrchextraBeacon>) data;

    InteractorResponse response = beaconCheckerDomainService.getCheckedBeaconList(orchextraBeacons);
    if (response.hasError()) {
      return response;
    }
    return triggerActionsFacadeDomainService.triggerActions((List<OrchextraBeacon>) response.getResult());
  }

  private InteractorResponse<List<BasicAction>> regionEventResult(ProximityEventType eventType) {
    OrchextraRegion detectedRegion = (OrchextraRegion) data;
    detectedRegion.setRegionEvent(eventType);

    InteractorResponse response = regionCheckerDomainService.obtainCheckedRegion(detectedRegion);

    if (response.hasError()) {
      return response;
    }

    if (eventType == ProximityEventType.REGION_EXIT) {
      if (response.getResult() instanceof OrchextraRegion) {
        triggerActionsFacadeDomainService.deleteScheduledActionIfExists(
            (ScheduledActionEvent) response.getResult());
      }
    }
    response = triggerActionsFacadeDomainService.triggerActions(detectedRegion);
    return response;
  }

  @Override public void updateEventWithAction(BasicAction basicAction) {
    switch (eventType) {
      case REGION_ENTER:
        OrchextraRegion detectedRegion = (OrchextraRegion) data;
        detectedRegion.setActionRelatedWithRegionAndGeofences(new ActionRelatedWithRegionAndGeofences(basicAction.getScheduledAction().getId(),
            basicAction.getScheduledAction().isCancelable()));
        eventUpdaterDomainService.associateActionToRegionEvent(detectedRegion);
      case REGION_EXIT:
        break;
      default:
        break;
    }
  }
}
