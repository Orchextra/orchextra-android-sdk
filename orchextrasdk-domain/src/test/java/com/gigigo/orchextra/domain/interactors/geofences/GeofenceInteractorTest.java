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
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.strategy.ScheduledActionImpl;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelated;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterDomainService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeDomainService;
import com.gigigo.orchextra.domain.services.geofences.GeofenceCheckerDomainService;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) public class GeofenceInteractorTest {

  @Mock
  TriggerActionsFacadeDomainService triggerActionsFacadeDomainService;

  @Mock
  GeofenceCheckerDomainService geofenceCheckerDomainService;

  @Mock
  EventUpdaterDomainService eventUpdaterDomainService;

  @Mock InteractorResponse interactorResponse;

  @Mock List list;

  @Mock InteractorResponse<List<OrchextraGeofence>> eventGeofenceList;

  private GeofenceInteractor interactor;

  private List idsList;

  @Before public void setUp() throws Exception {
    interactor = new GeofenceInteractor(triggerActionsFacadeDomainService, geofenceCheckerDomainService,
            eventUpdaterDomainService);

    buildGeofenceFakeList();
  }

  private void buildGeofenceFakeList() {
    idsList = new ArrayList<>();
    idsList.add("aaaa");
    idsList.add("bbbb");

    OrchextraPoint geofencePoint = new OrchextraPoint();
    geofencePoint.setLat(123);
    geofencePoint.setLng(321);
  }

  @Test public void shouldSaveEventGeofencesAndObtainGeofenceTriggerWhenRightDataIsPassed()
      throws Exception {
    GeoPointEventType eventType = GeoPointEventType.ENTER;
    interactor.setGeofenceData(idsList, eventType);

    when(geofenceCheckerDomainService.obtainEventGeofences(idsList, eventType)).thenReturn(
        eventGeofenceList);

    when(geofenceCheckerDomainService.obtainGeofencesById(idsList)).thenReturn(list);
    when(triggerActionsFacadeDomainService.triggerActions(list, eventType)).thenReturn(
        interactorResponse);

    interactor.call();

    verify(geofenceCheckerDomainService).obtainEventGeofences(idsList, eventType);
    verify(geofenceCheckerDomainService).obtainGeofencesById(idsList);
    verify(triggerActionsFacadeDomainService).triggerActions(list, eventType);
  }

  @Test public void shouldObtainEventGeofencesAndObtainGeofenceTrigger() throws Exception {
    GeoPointEventType eventType = GeoPointEventType.EXIT;
    interactor.setGeofenceData(idsList, eventType);

    List<OrchextraGeofence> orchextraGeofenceList = new ArrayList<>();
    OrchextraGeofence geofence = new OrchextraGeofence();
    orchextraGeofenceList.add(geofence);

    when(geofenceCheckerDomainService.obtainEventGeofences(idsList, eventType)).thenReturn(
        eventGeofenceList);
    when(eventGeofenceList.getResult()).thenReturn(orchextraGeofenceList);

    when(geofenceCheckerDomainService.obtainGeofencesById(idsList)).thenReturn(list);
    when(triggerActionsFacadeDomainService.triggerActions(list, eventType)).thenReturn(
        interactorResponse);

    interactor.call();

    verify(geofenceCheckerDomainService).obtainEventGeofences(idsList, eventType);
    verify(eventGeofenceList).getResult();
    verify(triggerActionsFacadeDomainService).deleteScheduledActionIfExists(geofence);
    verify(geofenceCheckerDomainService).obtainGeofencesById(idsList);
    verify(triggerActionsFacadeDomainService).triggerActions(list, eventType);
  }

  @Mock BusinessObject<OrchextraGeofence> boOrchextraGeofence;

  @Mock OrchextraGeofence orchextraGeofence;

  @Mock ScheduledActionImpl scheduledAction;

  @Test public void shouldUpdateEventGeofenceWhenNewAction() throws Exception {
    Schedule schedule = new Schedule(true, 1000);
    schedule.setEventId("456");

    BasicAction basicAction =
        new BrowserAction("0", "0", "http://www.google.es", new OrchextraNotification(), schedule);
    basicAction.setEventCode("AAA");

    when(geofenceCheckerDomainService.obtainCheckedGeofence(basicAction.getEventCode())).thenReturn(
        boOrchextraGeofence);

    when(boOrchextraGeofence.isSuccess()).thenReturn(true);
    when(boOrchextraGeofence.getData()).thenReturn(orchextraGeofence);
    when(orchextraGeofence.getCode()).thenReturn("AAA");

    interactor.updateEventWithAction(basicAction);

    verify(geofenceCheckerDomainService).obtainCheckedGeofence("AAA");
    verify(orchextraGeofence).setActionRelated(any(ActionRelated.class));
    verify(eventUpdaterDomainService).associateActionToGeofenceEvent(orchextraGeofence);
  }
}