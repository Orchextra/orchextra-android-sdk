package com.gigigo.orchextra.domain.abstractions.geofences;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;

public interface GeofenceRegister {
  void registerGeofences(OrchextraGeofenceUpdates geofenceUpdates);

  void startGeofenceRegister();

  void stopGeofenceRegister();
}
