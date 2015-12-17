package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class Device {

  private String handset;
  private String language;
  private String os;
  private String osVersion;
  private String timeZone;
  private String advertiserId;
  private String vendorId;
  private String instanceId;
  private String secureId;
  private String serialNumber;
  private String bluetoothMacAddress;
  private String wifiMacAddress;

  public String getHandset() {
    return handset;
  }

  public void setHandset(String handset) {
    this.handset = handset;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(String advertiserId) {
    this.advertiserId = advertiserId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
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

  public String getBluetoothMacAddress() {
    return bluetoothMacAddress;
  }

  public void setBluetoothMacAddress(String bluetoothMacAddress) {
    this.bluetoothMacAddress = bluetoothMacAddress;
  }

  public String getWifiMacAddress() {
    return wifiMacAddress;
  }

  public void setWifiMacAddress(String wifiMacAddress) {
    this.wifiMacAddress = wifiMacAddress;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }
}
