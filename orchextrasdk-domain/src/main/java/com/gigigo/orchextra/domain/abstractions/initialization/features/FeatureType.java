package com.gigigo.orchextra.domain.abstractions.initialization.features;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public enum FeatureType implements StringValueEnum {
  BEACONS ("BEACONS"),
  GOOGLE_PLAY_SERVICES("GOOGLE PLAY SERVICES");

  private final String type;

  FeatureType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
