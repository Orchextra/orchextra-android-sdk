package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveGeofenceTriggerInteractorTest {

    @Mock
    GeofenceDataProvider geofenceDataProvider;

    @Mock AppRunningMode appRunningMode;

    GeoPointEventType geoPointEventType;

    @Mock
    Point point;

    @Mock
    BusinessObject<Geofence> businessGeofence;

    @Mock Geofence geofence;

    double distanceFromGeofenceInKm = 234;

    private RetrieveGeofenceTriggerInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new RetrieveGeofenceTriggerInteractor(geofenceDataProvider, appRunningMode);
    }

    @Test
    public void shouldObtainGeofenceTriggerWhenRightDataIsPassed() throws Exception {
        List<String> idsList = new ArrayList<>();
        idsList.add("aaaa");
        idsList.add("bbbb");


        Point geofencePoint = new Point();
        geofencePoint.setLat(123);
        geofencePoint.setLng(321);

        interactor.setGeofenceTransition(geoPointEventType);
        interactor.setTriggeringPoint(point);
        interactor.setTriggeringGeofenceIds(idsList);

        when(geofenceDataProvider.obtainGeofenceByIdFromDatabase(anyString())).thenReturn(businessGeofence);
        when(geofenceDataProvider.obtainGeofenceByIdFromDatabase(anyString())).thenReturn(businessGeofence);
        when(businessGeofence.isSuccess()).thenReturn(true);
        when(businessGeofence.getData()).thenReturn(geofence);
        when(point.getDistanceFromPointInKm(geofence.getPoint())).thenReturn(distanceFromGeofenceInKm);
        when(appRunningMode.getRunningModeType()).thenReturn(AppRunningModeType.FOREGROUND);
        when(geofence.getPoint()).thenReturn(geofencePoint);

        InteractorResponse<List<Trigger>> response = interactor.call();

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertNull(response.getError());
        assertEquals(2, response.getResult().size());

        verify(geofenceDataProvider).obtainGeofenceByIdFromDatabase("aaaa");
        verify(geofenceDataProvider).obtainGeofenceByIdFromDatabase("bbbb");
        verify(businessGeofence, times(2)).isSuccess();
        verify(businessGeofence, times(2)).getData();
        verify(point, times(2)).getDistanceFromPointInKm(geofence.getPoint());
        verify(appRunningMode, times(2)).getRunningModeType();
        verify(geofence, times(6)).getPoint();
    }

    @Test
    public void shouldObtainErrorWhenGeofenceDoesntExist() throws Exception {
        List<String> idsList = new ArrayList<>();
        idsList.add("aaaa");
        idsList.add("bbbb");

        interactor.setTriggeringGeofenceIds(idsList);

        when(geofenceDataProvider.obtainGeofenceByIdFromDatabase(anyString())).thenReturn(businessGeofence);
        when(businessGeofence.isSuccess()).thenReturn(false);

        InteractorResponse response = interactor.call();

        assertNotNull(response);
        assertNull(response.getResult());
        assertNotNull(response.getError());

        verify(geofenceDataProvider).obtainGeofenceByIdFromDatabase(anyString());
        verify(businessGeofence).isSuccess();
    }

    @Test
    public void shouldObtainEmptyListWhenEmptyListIsPassed() throws Exception {
        List<String> idsList = new ArrayList<>();

        interactor.setTriggeringGeofenceIds(idsList);

        InteractorResponse<List<Trigger>> response = interactor.call();

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertNull(response.getError());
        assertEquals(0, response.getResult().size());

    }
}