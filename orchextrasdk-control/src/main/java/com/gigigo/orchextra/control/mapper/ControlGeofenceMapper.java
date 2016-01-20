package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.control.entities.ControlProximityPointType;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.ProximityPointType;

public class ControlGeofenceMapper implements Mapper<Geofence, ControlGeofence> {

    private final Mapper<Point, ControlPoint> controlPointMapper;
    private final Mapper<ProximityPointType, ControlProximityPointType> proximityPointTypeMapper;

    public ControlGeofenceMapper(Mapper<Point, ControlPoint> controlPointMapper,
                                 Mapper<ProximityPointType, ControlProximityPointType> proximityPointTypeMapper) {
        this.controlPointMapper = controlPointMapper;
        this.proximityPointTypeMapper = proximityPointTypeMapper;
    }

    @Override
    public ControlGeofence modelToControl(Geofence geofence) {
        ControlGeofence controlGeofence = new ControlGeofence();

        controlGeofence.setRadius(geofence.getRadius());
        controlGeofence.setPoint(controlPointMapper.modelToControl(geofence.getPoint()));

        controlGeofence.setCode(geofence.getCode());
        controlGeofence.setId(geofence.getId());
        controlGeofence.setName(geofence.getName());
        controlGeofence.setNotifyOnEntry(geofence.isNotifyOnEntry());
        controlGeofence.setNotifyOnExit(geofence.isNotifyOnExit());
        controlGeofence.setStayTime(geofence.getStayTime());
        controlGeofence.setTags(geofence.getTags());
        controlGeofence.setType(proximityPointTypeMapper.modelToControl(geofence.getType()));

        controlGeofence.setCreatedAt(geofence.getCreatedAt());

        controlGeofence.setUpdatedAt(geofence.getUpdatedAt());

        return controlGeofence;
    }

    @Override
    public Geofence controlToModel(ControlGeofence control) {
        Geofence geofence = new Geofence();

        geofence.setRadius(control.getRadius());
        geofence.setPoint(controlPointMapper.controlToModel(control.getPoint()));

        geofence.setCode(control.getCode());
        geofence.setId(control.getId());
        geofence.setName(control.getName());
        geofence.setNotifyOnEntry(control.isNotifyOnEntry());
        geofence.setNotifyOnExit(control.isNotifyOnExit());
        geofence.setStayTime(control.getStayTime());
        geofence.setTags(control.getTags());
        geofence.setType(proximityPointTypeMapper.controlToModel(control.getType()));

        geofence.setCreatedAt(control.getCreatedAt());

        geofence.setUpdatedAt(control.getUpdatedAt());

        return geofence;
    }
}
