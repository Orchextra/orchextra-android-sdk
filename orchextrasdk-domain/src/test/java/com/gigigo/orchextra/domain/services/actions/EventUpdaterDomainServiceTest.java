package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
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
public class EventUpdaterDomainServiceTest {

    @Mock
    OrchextraRegion mockOrchextraRegion;

    @Mock OrchextraGeofence mockOrchextraGeofence;

    @Mock
    ProximityAndGeofencesLocalDataProvider mockProximityAndGeofencesLocalDataProvider;

    @Mock
    BusinessObject<OrchextraRegion> mockBoOrchextraRegion;

    @Mock
    BusinessObject<OrchextraGeofence> mockBoOrchextraGeofence;

    private EventUpdaterDomainService eventUpdaterDomainService;

    @Before
    public void setUp() throws Exception {
        eventUpdaterDomainService = new EventUpdaterDomainService(mockProximityAndGeofencesLocalDataProvider);
    }

    @Test
    public void shouldReturnRegionWhenUpdateRegionIsSuccesful() throws Exception {
        when(mockProximityAndGeofencesLocalDataProvider.updateRegionWithActionId(mockOrchextraRegion)).thenReturn(mockBoOrchextraRegion);

        when(mockBoOrchextraRegion.isSuccess()).thenReturn(true);

        eventUpdaterDomainService.associateActionToRegionEvent(mockOrchextraRegion);

        verify(mockBoOrchextraRegion).getData();
    }

    @Test
    public void shouldReturnFalseWhenUpdateRegionIsFailed() throws Exception {
        when(mockProximityAndGeofencesLocalDataProvider.updateRegionWithActionId(mockOrchextraRegion)).thenReturn(mockBoOrchextraRegion);

        when(mockBoOrchextraRegion.isSuccess()).thenReturn(false);

        eventUpdaterDomainService.associateActionToRegionEvent(mockOrchextraRegion);

        verify(mockBoOrchextraRegion, never()).getData();
    }

    @Test
    public void shouldReturnGeofenceWhenUpdateGeofenceIsSuccessful() throws Exception {
        when(mockProximityAndGeofencesLocalDataProvider.updateGeofenceWithActionId(mockOrchextraGeofence)).thenReturn(mockBoOrchextraGeofence);

        when(mockBoOrchextraGeofence.isSuccess()).thenReturn(true);

        eventUpdaterDomainService.associateActionToGeofenceEvent(mockOrchextraGeofence);

        verify(mockBoOrchextraGeofence).getData();
    }

    @Test
    public void shouldReturnFalseWhenUpdateGeofenceIsFailed() throws Exception {
        when(mockProximityAndGeofencesLocalDataProvider.updateGeofenceWithActionId(mockOrchextraGeofence)).thenReturn(mockBoOrchextraGeofence);

        when(mockBoOrchextraGeofence.isSuccess()).thenReturn(false);

        eventUpdaterDomainService.associateActionToGeofenceEvent(mockOrchextraGeofence);

        verify(mockBoOrchextraGeofence, never()).getData();
    }
}