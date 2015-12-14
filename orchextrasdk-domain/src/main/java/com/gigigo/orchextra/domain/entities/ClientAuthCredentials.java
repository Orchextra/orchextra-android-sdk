package com.gigigo.orchextra.domain.entities;

import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class ClientAuthCredentials implements Credentials{

  private final String clientToken;
  private final String instanceId;
  private final String crmId;

  private String vendorId;
  private String advertiserId;
  private String secureId;
  private String serialNumber;
  private String wifiMacAddress;
  private String bluetoothMacAddress;

  public ClientAuthCredentials(String clientToken, String instanceId, String crmId,
      String vendorId) {
    this.clientToken = clientToken;
    this.instanceId = instanceId;
    this.crmId = crmId;
    this.vendorId = vendorId;
  }

  public ClientAuthCredentials(SdkAuthData data, DeviceDetailsProvider deviceDetailsProvider) {
    this.clientToken = data.getValue();
    this.instanceId = deviceDetailsProvider.getAndroidInstanceId();
    this.crmId = deviceDetailsProvider.getAndroidCrmId();
    this.vendorId = deviceDetailsProvider.getVendorId();
  }

  public String getClientToken() {
    return clientToken;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public String getCrmId() {
    return crmId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public String getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(String advertiserId) {
    this.advertiserId = advertiserId;
  }

  public String getSecureId() {
    return secureId;
  }

  public void setSecureId(String secureId) {
    this.secureId = secureId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getWifiMacAddress() {
    return wifiMacAddress;
  }

  public void setWifiMacAddress(String wifiMacAddress) {
    this.wifiMacAddress = wifiMacAddress;
  }

  public String getBluetoothMacAddress() {
    return bluetoothMacAddress;
  }

  public void setBluetoothMacAddress(String bluetoothMacAddress) {
    this.bluetoothMacAddress = bluetoothMacAddress;
  }
}
