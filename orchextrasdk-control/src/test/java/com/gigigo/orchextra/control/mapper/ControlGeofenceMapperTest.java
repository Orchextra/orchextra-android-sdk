package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.builders.ControlGeofenceBuilder;
import com.gigigo.orchextra.control.builders.ControlPointBuilder;
import com.gigigo.orchextra.control.builders.GeofenceBuilder;
import com.gigigo.orchextra.control.builders.PointBuilder;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.control.entities.ControlProximityPointType;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.ProximityPointType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControlGeofenceMapperTest {

    @Mock
    Mapper<Point, ControlPoint> controlPointMapper;

    @Mock
    Mapper<ProximityPointType, ControlProximityPointType> proximityPointTypeMapper;

    @Test
    public void shouldMapGeofenceToControlGeofence() throws Exception {

        Geofence geofence = GeofenceBuilder.Builder().build();

        when(controlPointMapper.modelToControl(geofence.getPoint())).thenReturn(ControlGeofenceBuilder.POINT);
        when(proximityPointTypeMapper.modelToControl(geofence.getType())).thenReturn(ControlGeofenceBuilder.TYPE);

        ControlGeofenceMapper mapper = new ControlGeofenceMapper(controlPointMapper, proximityPointTypeMapper);
        ControlGeofence controlGeofence = mapper.modelToControl(geofence);

        assertEquals(ControlPointBuilder.LAT, controlGeofence.getPoint().getLat(), 0.001);
        assertEquals(ControlPointBuilder.LNG, controlGeofence.getPoint().getLng(), 0.001);
        assertEquals(GeofenceBuilder.CODE, controlGeofence.getCode());
        assertEquals(GeofenceBuilder.ID, controlGeofence.getId());
        assertEquals(GeofenceBuilder.NAME, controlGeofence.getName());
        assertEquals(ControlGeofenceBuilder.TYPE, controlGeofence.getType());
        assertEquals(GeofenceBuilder.STAY, controlGeofence.getStayTime());
        assertEquals(GeofenceBuilder.RADIUS, controlGeofence.getRadius());
        assertEquals(GeofenceBuilder.UPDATED, controlGeofence.getUpdatedAt());
        assertEquals(GeofenceBuilder.CREATED, controlGeofence.getCreatedAt());
        assertEquals(1, controlGeofence.getTags().size());
        assertEquals(GeofenceBuilder.TAG_NAME, controlGeofence.getTags().get(0));
    }

    @Test
    public void shouldMapControlGeofenceToGeofence() throws Exception {

        ControlGeofence controlGeofence = ControlGeofenceBuilder.Builder().build();

        when(controlPointMapper.controlToModel(controlGeofence.getPoint())).thenReturn(GeofenceBuilder.POINT);
        when(proximityPointTypeMapper.controlToModel(controlGeofence.getType())).thenReturn(GeofenceBuilder.TYPE);

        ControlGeofenceMapper mapper = new ControlGeofenceMapper(controlPointMapper, proximityPointTypeMapper);
        Geofence geofence = mapper.controlToModel(controlGeofence);

        assertEquals(PointBuilder.LAT, geofence.getPoint().getLat(), 0.001);
        assertEquals(PointBuilder.LNG, geofence.getPoint().getLng(), 0.001);
        assertEquals(ControlGeofenceBuilder.CODE, geofence.getCode());
        assertEquals(ControlGeofenceBuilder.ID, geofence.getId());
        assertEquals(ControlGeofenceBuilder.NAME, geofence.getName());
        assertEquals(GeofenceBuilder.TYPE, geofence.getType());
        assertEquals(ControlGeofenceBuilder.STAY, geofence.getStayTime());
        assertEquals(ControlGeofenceBuilder.RADIUS, geofence.getRadius());
        assertEquals(ControlGeofenceBuilder.UPDATED, geofence.getUpdatedAt());
        assertEquals(ControlGeofenceBuilder.CREATED, geofence.getCreatedAt());
        assertEquals(1, geofence.getTags().size());
        assertEquals(ControlGeofenceBuilder.TAG_NAME, geofence.getTags().get(0));
    }
}