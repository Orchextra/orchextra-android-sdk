package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class RealGeofenceListImpl implements GeofenceList{

  private List<OrchextraGeofence> geofences;

  public RealGeofenceListImpl(List<OrchextraGeofence> beacons) {
    this.geofences = beacons;
  }

  @Override public List<OrchextraGeofence> getGeofences() {
    return geofences;
  }

  @Override public boolean isSupported() {
    return (geofences==null)? false : true;
  }
}
