package com.gigigo.orchextra.android.beacons;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BleNotAvailableException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public class BluetoothAvailabilityImpl implements BluetoothAvailability {

  private final BeaconManager beaconManager;

  public BluetoothAvailabilityImpl(BeaconManager beaconManager) {
    this.beaconManager = beaconManager;
  }

  @Override public boolean isBlteSupported() {

    try {
      beaconManager.checkAvailability();
    }catch (BleNotAvailableException blteSupported){
      return false;
    }

    return true;

  }

  @Override public boolean isBlteEnabled() {
    return beaconManager.checkAvailability();
  }
}
