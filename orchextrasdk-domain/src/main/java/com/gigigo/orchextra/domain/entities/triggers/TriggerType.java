package com.gigigo.orchextra.domain.entities.triggers;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum TriggerType implements StringValueEnum{
  BEACON("beacon"),
  GEOFENCE("geofence"),
  QR("qr"),
  BARCODE("barcode"),
  VUFORIA("vuforia");

  private final String type;

  TriggerType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
