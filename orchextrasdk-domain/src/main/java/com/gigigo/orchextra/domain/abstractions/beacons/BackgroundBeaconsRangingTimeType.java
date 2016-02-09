package com.gigigo.orchextra.domain.abstractions.beacons;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 27/1/16.
 */
public enum BackgroundBeaconsRangingTimeType {
  DISABLED(0),
  MIN(10000),
  //MAX(180000),
  MAX(180000),
  INFINITE(-1);

  private final int backgroundTimeMilis;

  BackgroundBeaconsRangingTimeType(final int backgroundTimeMilis) {
    this.backgroundTimeMilis = backgroundTimeMilis;
  }

  public int getIntValue() {
    return backgroundTimeMilis;
  }
}
