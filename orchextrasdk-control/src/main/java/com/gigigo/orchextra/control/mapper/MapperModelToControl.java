package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.entities.ControlGeofence;

import java.util.List;

public interface MapperModelToControl<M, P> {
  P modelToControl(List<ControlGeofence> geofencePointList);
}

