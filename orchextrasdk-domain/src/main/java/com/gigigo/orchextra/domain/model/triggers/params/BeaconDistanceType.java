package com.gigigo.orchextra.domain.model.triggers.params;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum BeaconDistanceType implements StringValueEnum {
  IMMEDIATE("immediate"),
  NEAR("near"),
  FAR("far");

  private final String type;

  BeaconDistanceType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

  public static BeaconDistanceType getValueFromString(String beaconDistance) {
    for (BeaconDistanceType beaconDistanceType : BeaconDistanceType.values()) {
      if (beaconDistanceType.getStringValue().equals(beaconDistance)) {
        return beaconDistanceType;
      }
    }
    return NEAR;
  }
}
