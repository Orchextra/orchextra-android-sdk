package com.gigigo.orchextra.device.bluetooth.beacons;

public enum BeaconBackgroundModeScan {
  NORMAL(10000L), //by default
  HARDCORE(34567L);
  //NORMAL use the standar values from buildconfig, for time scaning and time between. NO
  //HARDCORE ranging infinite,
  //never cancel qhen you are inside the region,
  //never set in background mode for beacon manager
  //when go from fore to back inside a region reinit ranging

  //@deprecated values now in BackgroundBeaconsRangingTimeType
  //WEAK(600000L),
  //MODERATE(120000L),
  //STRONG(60000L),
  //SEVERE(30000L),
  //EXTREME(10000L),
  private final long intensity;

  BeaconBackgroundModeScan(long intensity) {
    this.intensity = intensity;
  }

  public long getIntensity() {
    return intensity;
  }
}
