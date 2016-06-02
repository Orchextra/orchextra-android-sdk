package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventUpdaterServiceTest {

    @Mock
    OrchextraRegion mockOrchextraRegion;

    @Mock OrchextraGeofence mockOrchextraGeofence;

    @Mock
    ProximityLocalDataProvider mockProximityLocalDataProvider;

    @Mock
    BusinessObject<OrchextraRegion> mockBoOrchextraRegion;

    @Mock
    BusinessObject<OrchextraGeofence> mockBoOrchextraGeofence;

    private EventUpdaterService eventUpdaterService;

    @Before
    public void setUp() throws Exception {
        eventUpdaterService = new EventUpdaterService(mockProximityLocalDataProvider);
    }

    @Test
    public void shouldReturnRegionWhenUpdateRegionIsSuccesful() throws Exception {
        when(mockProximityLocalDataProvider.updateRegionWithActionId(mockOrchextraRegion)).thenReturn(mockBoOrchextraRegion);

        when(mockBoOrchextraRegion.isSuccess()).thenReturn(true);

        eventUpdaterService.associateActionToRegionEvent(mockOrchextraRegion);

        verify(mockBoOrchextraRegion).getData();
    }

    @Test
    public void shouldReturnFalseWhenUpdateRegionIsFailed() throws Exception {
        when(mockProximityLocalDataProvider.updateRegionWithActionId(mockOrchextraRegion)).thenReturn(mockBoOrchextraRegion);

        when(mockBoOrchextraRegion.isSuccess()).thenReturn(false);

        eventUpdaterService.associateActionToRegionEvent(mockOrchextraRegion);

        verify(mockBoOrchextraRegion, never()).getData();
    }

    @Test
    public void shouldReturnGeofenceWhenUpdateGeofenceIsSuccessful() throws Exception {
        when(mockProximityLocalDataProvider.updateGeofenceWithActionId(mockOrchextraGeofence)).thenReturn(mockBoOrchextraGeofence);

        when(mockBoOrchextraGeofence.isSuccess()).thenReturn(true);

        eventUpdaterService.associateActionToGeofenceEvent(mockOrchextraGeofence);

        verify(mockBoOrchextraGeofence).getData();
    }

    @Test
    public void shouldReturnFalseWhenUpdateGeofenceIsFailed() throws Exception {
        when(mockProximityLocalDataProvider.updateGeofenceWithActionId(mockOrchextraGeofence)).thenReturn(mockBoOrchextraGeofence);

        when(mockBoOrchextraGeofence.isSuccess()).thenReturn(false);

        eventUpdaterService.associateActionToGeofenceEvent(mockOrchextraGeofence);

        verify(mockBoOrchextraGeofence, never()).getData();
    }
}