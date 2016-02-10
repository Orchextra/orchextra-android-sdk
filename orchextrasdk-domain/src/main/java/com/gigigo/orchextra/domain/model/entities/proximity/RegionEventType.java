package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.StringValueEnum;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public enum RegionEventType  implements StringValueEnum {
  ENTER(GeoPointEventType.ENTER.getStringValue()),
  EXIT(GeoPointEventType.EXIT.getStringValue());

  private final String type;

  RegionEventType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
