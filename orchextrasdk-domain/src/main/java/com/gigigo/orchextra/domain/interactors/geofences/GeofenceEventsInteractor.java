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

package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelatedWithRegionAndGeofences;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.services.actions.EventAccessor;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterDomainService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeDomainService;
import com.gigigo.orchextra.domain.services.geofences.GeofenceCheckerDomainService;

import java.util.List;

public class GeofenceEventsInteractor
    implements Interactor<InteractorResponse<List<BasicAction>>>, EventAccessor {

  private final TriggerActionsFacadeDomainService triggerActionsFacadeDomainService;
  private final GeofenceCheckerDomainService geofenceCheckerDomainService;
  private final EventUpdaterDomainService eventUpdaterDomainService;

  private List<String> triggeringGeofenceIds;
  private GeoPointEventType geofenceTransition;

  public GeofenceEventsInteractor(TriggerActionsFacadeDomainService triggerActionsFacadeDomainService,
                                  GeofenceCheckerDomainService geofenceCheckerDomainService, EventUpdaterDomainService eventUpdaterDomainService) {

    this.triggerActionsFacadeDomainService = triggerActionsFacadeDomainService;
    this.geofenceCheckerDomainService = geofenceCheckerDomainService;
    this.eventUpdaterDomainService = eventUpdaterDomainService;

    triggerActionsFacadeDomainService.setEventAccessor(this);
  }

  @Override public InteractorResponse<List<BasicAction>> call() throws Exception {
    registerEventGeofences();

    List<OrchextraGeofence> orchextraGeofenceList =
        geofenceCheckerDomainService.obtainGeofencesById(triggeringGeofenceIds);
    return triggerActionsFacadeDomainService.triggerActions(orchextraGeofenceList, geofenceTransition);
  }

  private void registerEventGeofences() {
    InteractorResponse<List<OrchextraGeofence>> response =
        geofenceCheckerDomainService.obtainEventGeofences(triggeringGeofenceIds, geofenceTransition);

    if (geofenceTransition == GeoPointEventType.EXIT) {
      for (OrchextraGeofence geofence : response.getResult()) {
        triggerActionsFacadeDomainService.deleteScheduledActionIfExists(geofence);
      }
    }
  }

  @Override public void updateEventWithAction(BasicAction basicAction) {
    BusinessObject<OrchextraGeofence> boGeofence =
        geofenceCheckerDomainService.obtainCheckedGeofence(basicAction.getEventCode());
    if (boGeofence.isSuccess() && boGeofence.getData()
        .getCode()
        .equals(basicAction.getEventCode())) {
      OrchextraGeofence geofence = boGeofence.getData();
      geofence.setActionRelatedWithRegionAndGeofences(new ActionRelatedWithRegionAndGeofences(basicAction.getScheduledAction().getId(),
          basicAction.getScheduledAction().isCancelable()));
      eventUpdaterDomainService.associateActionToGeofenceEvent(geofence);
    }
  }

  public void setGeofenceData(List<String> triggeringGeofenceIds,
      GeoPointEventType geofenceTransition) {
    this.triggeringGeofenceIds = triggeringGeofenceIds;
    this.geofenceTransition = geofenceTransition;
  }
}
