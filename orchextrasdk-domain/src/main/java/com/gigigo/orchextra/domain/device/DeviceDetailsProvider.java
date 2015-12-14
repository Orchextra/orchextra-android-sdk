package com.gigigo.orchextra.domain.device;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public interface DeviceDetailsProvider {
  String getVendorId();
  String getAndroidInstanceId();
  String getAndroidCrmId();
  String getAndroidSecureId();
  String getAndroidSerialNumner();
  String getWifiMac();
  String bluetoothMac();
}
