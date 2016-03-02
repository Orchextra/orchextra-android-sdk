package com.gigigo.orchextra.domain.model.triggers.params;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum GeoPointEventType implements StringValueEnum {
  ENTER("enter"),
  EXIT("exit"),
  STAY("stay");

  private final String type;

  GeoPointEventType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

  public static GeoPointEventType getTypeFromString(String stringValue) {
    for (GeoPointEventType geoPointEventType : values()) {
      if (geoPointEventType.getStringValue().equals(stringValue)) {
        return geoPointEventType;
      }
    }
    return ENTER;
  }
}
