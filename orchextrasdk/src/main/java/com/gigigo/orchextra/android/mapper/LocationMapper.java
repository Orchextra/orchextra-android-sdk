package com.gigigo.orchextra.android.mapper;

import android.location.Location;

import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import com.gigigo.orchextra.domain.mappers.MapperAndroidToModel;

public class LocationMapper implements MapperAndroidToModel<Location, OrchextraPoint> {

    @Override public OrchextraPoint androidToModel(Location location) {
        OrchextraPoint point = new OrchextraPoint();

        point.setLat(location.getLatitude());
        point.setLng(location.getLongitude());

        return point;
    }
}
