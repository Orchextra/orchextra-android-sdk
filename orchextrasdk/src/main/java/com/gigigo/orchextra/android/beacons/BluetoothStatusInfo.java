package com.gigigo.orchextra.android.beacons;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface BluetoothStatusInfo {
  void obtainBluetoothStatus();

  void setBluetoothStatusListener(BluetoothStatusListener bluetoothStatusListener);
}
