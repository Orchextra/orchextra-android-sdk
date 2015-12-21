package com.gigigo.orchextra.domain.entities.triggers;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum BeaconDistanceType implements StringValueEnum{
  INMEDIATE("inmediate"),
  NEAR("near"),
  FAR("far");

  private final String type;

  BeaconDistanceType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
