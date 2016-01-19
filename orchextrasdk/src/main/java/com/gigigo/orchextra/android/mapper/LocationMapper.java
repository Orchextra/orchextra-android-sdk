package com.gigigo.orchextra.android.mapper;

import android.location.Location;

import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.control.mapper.MapperControlToModel;

public class LocationMapper implements MapperControlToModel<ControlPoint, Location> {

    public ControlPoint controlToModel(Location control) {
        ControlPoint point = new ControlPoint();

        point.setLat(control.getLatitude());
        point.setLng(control.getLongitude());

        return point;
    }
}
