package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.MethodSupported;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface GeofenceList extends MethodSupported {
  List<Geofence> getGeofences();
}
