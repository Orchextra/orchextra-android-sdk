package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.Geofence;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class RealGeofenceListImpl implements GeofenceList{

  private List<Geofence> geofences;

  public RealGeofenceListImpl(List<Geofence> beacons) {
    this.geofences = beacons;
  }

  @Override public List<Geofence> getGeofences() {
    return geofences;
  }

  @Override public boolean isSupported() {
    return (geofences==null)? false : true;
  }
}
