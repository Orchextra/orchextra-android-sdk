package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.strategy.ScheduledActionImpl;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelated;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.proximity.GeofenceCheckerService;
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

  @Mock TriggerActionsFacadeService triggerActionsFacadeService;

  @Mock GeofenceCheckerService geofenceCheckerService;

  @Mock EventUpdaterService eventUpdaterService;

  @Mock InteractorResponse interactorResponse;

  @Mock List list;

  @Mock InteractorResponse<List<OrchextraGeofence>> eventGeofenceList;

  private GeofenceInteractor interactor;

  private List idsList;

  @Before public void setUp() throws Exception {
    interactor = new GeofenceInteractor(triggerActionsFacadeService, geofenceCheckerService,
        eventUpdaterService);

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

    when(geofenceCheckerService.obtainEventGeofences(idsList, eventType)).thenReturn(
        eventGeofenceList);

    when(geofenceCheckerService.obtainGeofencesById(idsList)).thenReturn(list);
    when(triggerActionsFacadeService.triggerActions(list, eventType)).thenReturn(
        interactorResponse);

    interactor.call();

    verify(geofenceCheckerService).obtainEventGeofences(idsList, eventType);
    verify(geofenceCheckerService).obtainGeofencesById(idsList);
    verify(triggerActionsFacadeService).triggerActions(list, eventType);
  }

  @Test public void shouldObtainEventGeofencesAndObtainGeofenceTrigger() throws Exception {
    GeoPointEventType eventType = GeoPointEventType.EXIT;
    interactor.setGeofenceData(idsList, eventType);

    List<OrchextraGeofence> orchextraGeofenceList = new ArrayList<>();
    OrchextraGeofence geofence = new OrchextraGeofence();
    orchextraGeofenceList.add(geofence);

    when(geofenceCheckerService.obtainEventGeofences(idsList, eventType)).thenReturn(
        eventGeofenceList);
    when(eventGeofenceList.getResult()).thenReturn(orchextraGeofenceList);

    when(geofenceCheckerService.obtainGeofencesById(idsList)).thenReturn(list);
    when(triggerActionsFacadeService.triggerActions(list, eventType)).thenReturn(
        interactorResponse);

    interactor.call();

    verify(geofenceCheckerService).obtainEventGeofences(idsList, eventType);
    verify(eventGeofenceList).getResult();
    verify(triggerActionsFacadeService).deleteScheduledActionIfExists(geofence);
    verify(geofenceCheckerService).obtainGeofencesById(idsList);
    verify(triggerActionsFacadeService).triggerActions(list, eventType);
  }

  @Mock BusinessObject<OrchextraGeofence> boOrchextraGeofence;

  @Mock OrchextraGeofence orchextraGeofence;

  @Mock ScheduledActionImpl scheduledAction;

  @Test public void shouldUpdateEventGeofenceWhenNewAction() throws Exception {
    Schedule schedule = new Schedule(true, 1000);
    schedule.setEventId("456");

    BasicAction basicAction =
        new BrowserAction("0", "0", "http://www.google.es", new Notification(), schedule);
    basicAction.setEventCode("AAA");

    when(geofenceCheckerService.obtainCheckedGeofence(basicAction.getEventCode())).thenReturn(
        boOrchextraGeofence);

    when(boOrchextraGeofence.isSuccess()).thenReturn(true);
    when(boOrchextraGeofence.getData()).thenReturn(orchextraGeofence);
    when(orchextraGeofence.getCode()).thenReturn("AAA");

    interactor.updateEventWithAction(basicAction);

    verify(geofenceCheckerService).obtainCheckedGeofence("AAA");
    verify(orchextraGeofence).setActionRelated(any(ActionRelated.class));
    verify(eventUpdaterService).associateActionToGeofenceEvent(orchextraGeofence);
  }
}