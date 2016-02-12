package com.gigigo.orchextra.android.proximity.geofencing;

import android.location.Location;

import com.gigigo.orchextra.android.builders.ControlGeofenceBuilder;
import com.gigigo.orchextra.android.builders.OrchextraPointBuilder;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AndroidGeofenceManagerTest {

    @Mock AndroidGeofenceConverter androidGeofenceMapper;

    @Mock GeofenceDeviceRegister geofenceDeviceRegister;

    @Mock
    LocationMapper locationMapper;

    @Mock
    GeofencingRequest geofencingRequest;

    @Mock
    GeofencingEvent geofencingEvent;

    @Mock
    Location location;

    private AndroidGeofenceRegister manager;

    @Before
    public void setUp() throws Exception {
        manager = new AndroidGeofenceRegister(androidGeofenceMapper, geofenceDeviceRegister, locationMapper);
    }

    @Test
    public void shouldRegisterGeofencesWhenIsFillGeofenceList() throws Exception {
        List<OrchextraGeofence> geofenceList = new ArrayList<>();
        geofenceList.add(ControlGeofenceBuilder.Builder().build());

        when(androidGeofenceMapper.convertGeofencesToGeofencingRequest(geofenceList)).thenReturn(geofencingRequest);

        manager.registerGeofences(geofenceList);

        verify(geofenceDeviceRegister).register(geofencingRequest);
    }

    @Test
    public void shouldObtainTriggerPointWithIntent() throws Exception {
        OrchextraPoint controlPoint = OrchextraPointBuilder.Builder().build();

        when(geofencingEvent.getTriggeringLocation()).thenReturn(location);
        when(locationMapper.externalClassToModel(location)).thenReturn(controlPoint);

        OrchextraPoint triggeringPoint = manager.getTriggeringPoint(geofencingEvent);

        assertEquals(OrchextraPointBuilder.LAT, triggeringPoint.getLat(), 0.0001);
        assertEquals(OrchextraPointBuilder.LNG, triggeringPoint.getLng(), 0.0001);
    }

    @Test
    public void shouldObtainTriggeringGeofencesWithIntent() throws Exception {

        List<Geofence> geofenceList = new ArrayList<>();
        Geofence geofence = new Geofence() {
            @Override
            public String getRequestId() {
                return "aaa";
            }
        };
        geofenceList.add(geofence);

        when(geofencingEvent.getTriggeringGeofences()).thenReturn(geofenceList);

        List<String> triggeringGeofenceIds = manager.getTriggeringGeofenceIds(geofencingEvent);

        assertEquals(1, triggeringGeofenceIds.size());
        assertEquals("aaa", triggeringGeofenceIds.get(0));
    }
}