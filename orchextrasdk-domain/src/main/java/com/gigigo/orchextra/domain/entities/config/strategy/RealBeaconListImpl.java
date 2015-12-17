package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.Beacon;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class RealBeaconListImpl implements BeaconList{

  private List<Beacon> beacons;

  public RealBeaconListImpl(List<Beacon> beacons) {
    this.beacons = beacons;
  }

  @Override public List<Beacon> getBeacons() {
    return beacons;
  }

  @Override public boolean isSupported() {
    return (beacons==null)? false : true;
  }
}
