package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.entities.ControlProximityPointType;
import com.gigigo.orchextra.domain.entities.ProximityPointType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControlProximityPointTypeMapperTest {

    @Test
    public void shouldMappedBeaconProximityPointTypeToControlLayer() throws Exception {

        ProximityPointType proximityPointType = ProximityPointType.BEACON;

        ControlProximityPointTypeMapper mapper = new ControlProximityPointTypeMapper();
        ControlProximityPointType controlProximityPointType = mapper.modelToControl(proximityPointType);

        assertEquals(ProximityPointType.BEACON.getStringValue(), controlProximityPointType.getStringValue());
    }

    @Test
    public void shouldMappedGeofenceProximityPointTypeToControlLayer() throws Exception {

        ProximityPointType proximityPointType = ProximityPointType.GEOFENCE;

        ControlProximityPointTypeMapper mapper = new ControlProximityPointTypeMapper();
        ControlProximityPointType controlProximityPointType = mapper.modelToControl(proximityPointType);

        assertEquals(ProximityPointType.GEOFENCE.getStringValue(), controlProximityPointType.getStringValue());
    }

    @Test
    public void shouldMappedBeaconProximityPointTypeToDomainLayer() throws Exception {
        ControlProximityPointType controlProximityPointType = ControlProximityPointType.BEACON;

        ControlProximityPointTypeMapper mapper = new ControlProximityPointTypeMapper();
        ProximityPointType proximityPointType = mapper.controlToModel(controlProximityPointType);

        assertEquals(ControlProximityPointType.BEACON.getStringValue(), proximityPointType.getStringValue());
    }

    @Test
    public void shouldMappedGeofenceProximityPointTypeToDomainLayer() throws Exception {
        ControlProximityPointType controlProximityPointType = ControlProximityPointType.GEOFENCE;

        ControlProximityPointTypeMapper mapper = new ControlProximityPointTypeMapper();
        ProximityPointType proximityPointType = mapper.controlToModel(controlProximityPointType);

        assertEquals(ControlProximityPointType.GEOFENCE.getStringValue(), proximityPointType.getStringValue());
    }
}