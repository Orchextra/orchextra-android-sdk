package com.gigigo.orchextra.domain.device;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public interface DeviceDetailsProvider {
  String getAndroidInstanceId();
  String getAndroidSecureId();
  String getAndroidSerialNumber();
  String getWifiMac();
  String getBluetoothMac();
}
