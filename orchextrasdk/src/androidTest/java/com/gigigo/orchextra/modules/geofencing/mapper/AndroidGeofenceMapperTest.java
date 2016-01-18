package com.gigigo.orchextra.modules.geofencing.mapper;


import com.gigigo.orchextra.builders.GeofenceBuilder;
import com.gigigo.orchextra.builders.PointBuilder;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AndroidGeofenceMapperTest {

    private AndroidGeofenceMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new AndroidGeofenceMapper();
    }

    @Test
    public void shouldReturnGeofencingRequestWithGeofenceList() throws Exception {
        List<Geofence> geofenceList = new ArrayList<>();
        geofenceList.add(GeofenceBuilder.Builder().setCode("345").build());
        geofenceList.add(GeofenceBuilder.Builder()
                .setPoint(PointBuilder.Builder()
                                .setLat(56.43)
                                .setLng(-23.45)
                                .build()
                )
                .setCode("Asxe")
                .build());

        GeofencingRequest geofencingRequest = mapper.modelToControl(geofenceList);

        assertThat(geofenceList.size(), is(geofencingRequest.getGeofences().size()));
        assertThat(geofenceList.get(0).getCode(), is(geofencingRequest.getGeofences().get(0).getRequestId()));
        assertThat(geofenceList.get(1).getCode(), is(geofencingRequest.getGeofences().get(1).getRequestId()));
        assertThat(0, is(geofencingRequest.getInitialTrigger()));
        assertThat(0, is(geofencingRequest.getVersionCode()));
    }
}