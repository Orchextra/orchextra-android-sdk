package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.proximity.GeofenceCheckerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeofenceInteractorTest {

    @Mock
    TriggerActionsFacadeService triggerActionsFacadeService;

    @Mock
    GeofenceCheckerService geofenceCheckerService;

    @Mock
    EventUpdaterService eventUpdaterService;

    @Mock OrchextraPoint point;

    @Mock
    BusinessObject<OrchextraGeofence> businessGeofence;

    @Mock OrchextraGeofence geofence;

    @Mock InteractorResponse interactorResponse;

    @Mock List list;

    private GeofenceInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new GeofenceInteractor(triggerActionsFacadeService, geofenceCheckerService, eventUpdaterService);
    }

    @Test
    public void shouldObtainGeofenceTriggerWhenRightDataIsPassed() throws Exception {
        List<String> idsList = new ArrayList<>();
        idsList.add("aaaa");
        idsList.add("bbbb");

        GeoPointEventType eventType = GeoPointEventType.ENTER;

        OrchextraPoint geofencePoint = new OrchextraPoint();
        geofencePoint.setLat(123);
        geofencePoint.setLng(321);

        interactor.setGeofenceData(idsList, point, eventType);

        when(geofenceCheckerService.obtainCheckedGeofences(idsList, eventType)).thenReturn(interactorResponse);
        when(interactorResponse.getResult()).thenReturn(list);
        when(list.size()).thenReturn(1);
        when(triggerActionsFacadeService.triggerActions(list, eventType)).thenReturn(interactorResponse);

        InteractorResponse<List<BasicAction>> response = interactor.call();

        verify(geofenceCheckerService).obtainCheckedGeofences(idsList, eventType);
        verify(interactorResponse, times(2)).getResult();
        verify(triggerActionsFacadeService).triggerActions(list, eventType);
    }


//    @Test
//    public void shouldObtainErrorWhenGeofenceDoesntExist() throws Exception {
//        List<String> idsList = new ArrayList<>();
//        idsList.add("aaaa");
//        idsList.add("bbbb");
//
//        interactor.setTriggeringGeofenceIds(idsList);
//
//        when(proximityLocalDataProvider.obtainGeofence(anyString())).thenReturn(businessGeofence);
//        when(businessGeofence.isSuccess()).thenReturn(false);
//
//        InteractorResponse response = interactor.call();
//
//        assertNotNull(response);
//        assertNull(response.getResult());
//        assertNotNull(response.getError());
//
//        verify(proximityLocalDataProvider).obtainGeofence(anyString());
//        verify(businessGeofence).isSuccess();
//    }
//
//    @Test
//    public void shouldObtainEmptyListWhenEmptyListIsPassed() throws Exception {
//        List<String> idsList = new ArrayList<>();
//
//        interactor.setTriggeringGeofenceIds(idsList);
//
//        InteractorResponse<List<Trigger>> response = interactor.call();
//
//        assertNotNull(response);
//        assertNotNull(response.getResult());
//        assertNull(response.getError());
//        assertEquals(0, response.getResult().size());
//
//    }
}