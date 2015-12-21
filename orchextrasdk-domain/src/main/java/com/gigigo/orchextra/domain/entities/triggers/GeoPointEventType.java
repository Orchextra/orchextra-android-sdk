package com.gigigo.orchextra.domain.entities.triggers;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum GeoPointEventType implements StringValueEnum{
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
}
