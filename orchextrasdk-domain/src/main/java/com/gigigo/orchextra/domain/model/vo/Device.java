/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.model.vo;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class Device {

  private String handset;
  private String osVersion;
  private String language;
  private String timeZone;
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

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
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
}
