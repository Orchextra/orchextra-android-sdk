package com.gigigo.orchextra.utils.mapper;

import android.location.Location;

import com.gigigo.orchextra.domain.entities.Point;

public class LocationMapper implements MapperDelegateToModel<Point, Location> {

    public Point delegateToModel(Location delegate) {
        Point point = new Point();

        point.setLat(delegate.getLatitude());
        point.setLng(delegate.getLongitude());

        return point;
    }
}
