package com.gigigo.orchextra.control.entities.mappers;

import com.gigigo.orchextra.control.entities.GeofenceControl;
import com.gigigo.orchextra.domain.entities.Geofence;

public class GeofenceControlMapper implements Mapper<Geofence, GeofenceControl> {

    private final PointControlMapper pointControlMapper;

    public GeofenceControlMapper(PointControlMapper pointControlMapper) {
        this.pointControlMapper = pointControlMapper;
    }

    @Override
    public GeofenceControl modelToData(Geofence geofence) {
        GeofenceControl geofenceControl = new GeofenceControl();

        geofenceControl.setRadius(geofence.getRadius());
        geofenceControl.setCode(geofence.getCode());
        geofenceControl.setId(geofence.getId());
        geofenceControl.setName(geofence.getName());
        geofenceControl.setNotifyOnEntry(geofence.isNotifyOnEntry());
        geofenceControl.setNotifyOnExit(geofence.isNotifyOnExit());
        geofenceControl.setStayTime(geofence.getStayTime());
        geofenceControl.setTags(geofence.getTags());
        geofenceControl.setType(geofence.getType());

        geofenceControl.setCreatedAt(geofence.getCreatedAt());
        geofenceControl.setUpdatedAt(geofence.getUpdatedAt());

        geofenceControl.setPoint(pointControlMapper.modelToData(geofence.getPoint()));

        return geofenceControl;
    }

    @Override
    public Geofence dataToModel(GeofenceControl geofenceControl) {
        Geofence geofence = new Geofence();

        geofence.setRadius(geofenceControl.getRadius());
        geofence.setCode(geofenceControl.getCode());
        geofence.setId(geofenceControl.getId());
        geofence.setName(geofenceControl.getName());
        geofence.setNotifyOnEntry(geofenceControl.isNotifyOnEntry());
        geofence.setNotifyOnExit(geofenceControl.isNotifyOnExit());
        geofence.setStayTime(geofenceControl.getStayTime());
        geofence.setTags(geofenceControl.getTags());
        geofence.setType(geofenceControl.getType());

        geofence.setCreatedAt(geofenceControl.getCreatedAt());
        geofence.setUpdatedAt(geofenceControl.getUpdatedAt());

        geofence.setPoint(pointControlMapper.dataToModel(geofenceControl.getPoint()));

        return geofence;
    }
}
