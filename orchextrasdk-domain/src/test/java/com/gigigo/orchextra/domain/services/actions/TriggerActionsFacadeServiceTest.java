package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.services.triggers.TriggerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TriggerActionsFacadeServiceTest {

    @Mock
    List<OrchextraBeacon> orchextraBeaconList;

    @Mock
    OrchextraRegion orchextraRegion;

    @Mock
    List<OrchextraGeofence> orchextraGeofenceList;

    @Mock
    ScannerResult scannerResult;

    @Mock
    TriggerService triggerService;

    @Mock
    GetActionService getActionService;

    @Mock ScheduleActionService scheduleActionService;

    @Mock
    InteractorResponse triggerInteractorResponse;

    @Mock
    InteractorResponse actionInteractorResponse;

    @Mock BeaconEventsInteractor beaconEventsInteractor;

    private TriggerActionsFacadeService triggerActionsFacadeService;

    @Before
    public void setUp() throws Exception {
        triggerActionsFacadeService = new TriggerActionsFacadeService(triggerService, getActionService, scheduleActionService);
    }

    @Test
    public void shouldRetrieveResponseErrorWhentGetTriggerRequesFails() throws Exception {
        when(triggerService.getTrigger(orchextraBeaconList)).thenReturn(triggerInteractorResponse);

        when(triggerInteractorResponse.hasError()).thenReturn(true);

        InteractorResponse<List<BasicAction>> response = triggerActionsFacadeService.triggerActions(orchextraBeaconList);

        assertTrue(response.hasError());
    }

    @Test
    public void shouldRetrieveResponseErrorWhenObtainActionRequestFails() throws Exception {
        when(triggerService.getTrigger(orchextraRegion)).thenReturn(triggerInteractorResponse);

        when(triggerInteractorResponse.hasError()).thenReturn(false);

        when(getActionService.getActions((List<Trigger>) triggerInteractorResponse.getResult())).thenReturn(actionInteractorResponse);

        when(actionInteractorResponse.hasError()).thenReturn(true);

        InteractorResponse<List<BasicAction>> response = triggerActionsFacadeService.triggerActions(orchextraRegion);

        assertTrue(response.hasError());
    }

    @Test
    public void shouldReturnOneActionWhenTriggerDoesntHaveScheduledActions() throws Exception {
        List<BasicAction> actions = new ArrayList<>();
        BasicAction basicAction = new BrowserAction("1234", "1213", "http://www.google.es", null, null);
        actions.add(basicAction);

        when(triggerService.getTrigger(orchextraGeofenceList, GeoPointEventType.ENTER)).thenReturn(triggerInteractorResponse);

        when(triggerInteractorResponse.hasError()).thenReturn(false);

        when(getActionService.getActions((List<Trigger>) triggerInteractorResponse.getResult())).thenReturn(actionInteractorResponse);

        when(actionInteractorResponse.hasError()).thenReturn(false);

        when(actionInteractorResponse.getResult()).thenReturn(actions);

        InteractorResponse<List<BasicAction>> response = triggerActionsFacadeService.triggerActions(orchextraGeofenceList, GeoPointEventType.ENTER);

        assertThat(response.getResult().size(), is(1));
        assertThat(response.getResult().get(0), is(basicAction));
    }

    @Test
    public void shouldReturnEmptyActionListWhenTriggersHaveScheduledActions() throws Exception {
        List<BasicAction> actions = new ArrayList<>();
        BasicAction basicAction = new BrowserAction("1234", "1213", "http://www.google.es", new OrchextraNotification(), new Schedule(true, 1000));
        actions.add(basicAction);

        when(triggerService.getTrigger(scannerResult, null)).thenReturn(triggerInteractorResponse);

        when(triggerInteractorResponse.hasError()).thenReturn(false);

        when(getActionService.getActions((List<Trigger>) triggerInteractorResponse.getResult())).thenReturn(actionInteractorResponse);

        when(actionInteractorResponse.hasError()).thenReturn(false);

        when(actionInteractorResponse.getResult()).thenReturn(actions);

        triggerActionsFacadeService.setEventAccessor(beaconEventsInteractor);
        InteractorResponse<List<BasicAction>> response = triggerActionsFacadeService.triggerActions(scannerResult, null);

        assertThat(response.getResult().size(), is(0));
    }
}