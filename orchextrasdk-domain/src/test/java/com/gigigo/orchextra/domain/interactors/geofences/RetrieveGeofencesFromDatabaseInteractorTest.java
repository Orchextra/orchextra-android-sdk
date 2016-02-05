package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveGeofencesFromDatabaseInteractorTest {

    @Mock
    GeofenceDataProvider geofenceDataProvider;

    @Mock
    BusinessObject<List<OrchextraGeofence>> businessObject;

    @Mock List<OrchextraGeofence> geofenceList;

    private RetrieveGeofencesFromDatabaseInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new RetrieveGeofencesFromDatabaseInteractor(geofenceDataProvider);
    }

    @Test
    public void shouldObtainGeofenceListFromDatabase() throws Exception {
        when(geofenceDataProvider.obtainGeofencesFromDatabase()).thenReturn(businessObject);
        when(businessObject.isSuccess()).thenReturn(true);
        when(businessObject.getData()).thenReturn(geofenceList);

        InteractorResponse response = interactor.call();

        assertNotNull(response);
        assertNull(response.getError());
        assertNotNull(response.getResult());

        verify(geofenceDataProvider).obtainGeofencesFromDatabase();
        verify(businessObject).isSuccess();
        verify(businessObject).getData();
    }

    @Test
    public void shouldReturnErrorWhenRequestIsNotSuccess() throws Exception {
        when(geofenceDataProvider.obtainGeofencesFromDatabase()).thenReturn(businessObject);
        when(businessObject.isSuccess()).thenReturn(false);

        InteractorResponse response = interactor.call();

        assertNotNull(response);
        assertNotNull(response.getError());
        assertNull(response.getResult());

        verify(geofenceDataProvider).obtainGeofencesFromDatabase();
        verify(businessObject).isSuccess();
        verify(businessObject, never()).getData();
    }
}