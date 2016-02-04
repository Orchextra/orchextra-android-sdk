package com.gigigo.orchextra.android.beacons;

import com.gigigo.orchextra.domain.entities.triggers.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public enum BluetoothStatus implements StringValueEnum {
  NO_BLTE_SUPPORTED("Bluetooth LE Not Supported"),
  NO_PERMISSIONS("Bluetooth permissions not granted"),
  NOT_ENABLED("Bluetooth Not Enabled, But Beacons are OK"),
  READY_FOR_SCAN ("Bluetooth ON and Beacons ready to scan");

  private final String type;

  BluetoothStatus(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
