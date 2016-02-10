package com.gigigo.orchextra.device.geolocation.geofencing.mapper;

import android.location.Location;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

public class LocationMapper implements ExternalClassToModelMapper<Location, OrchextraPoint> {

    @Override public OrchextraPoint externalClassToModel(Location location) {
        OrchextraPoint point = new OrchextraPoint();

        point.setLat(location.getLatitude());
        point.setLng(location.getLongitude());

        return point;
    }
}
