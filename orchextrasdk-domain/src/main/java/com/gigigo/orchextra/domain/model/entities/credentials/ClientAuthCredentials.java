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

package com.gigigo.orchextra.domain.model.entities.credentials;

import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class ClientAuthCredentials implements Credentials {

  private final String clientToken;
  private final String instanceId;

  private String secureId;
  private String crmId;
  private String serialNumber;
  private String wifiMacAddress;
  private String bluetoothMacAddress;

  public ClientAuthCredentials(String clientToken, String instanceId) {
    this.clientToken = clientToken;
    this.instanceId = instanceId;
  }

  public ClientAuthCredentials(SdkAuthData data, DeviceDetailsProvider deviceDetailsProvider,
      String crmId) {
    this(data.getValue(), deviceDetailsProvider.getAndroidInstanceId());
    this.secureId = deviceDetailsProvider.getAndroidSecureId();
    this.serialNumber = deviceDetailsProvider.getAndroidSerialNumber();
    this.wifiMacAddress = deviceDetailsProvider.getWifiMac();
    this.bluetoothMacAddress = deviceDetailsProvider.getBluetoothMac();
    this.crmId = crmId;
  }

  public String getClientToken() {
    return clientToken;
  }

  public String getInstanceId() {
    return instanceId;
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

  public String getCrmId() {
    return crmId;
  }

  public void setCrmId(String crmId) {
    this.crmId = crmId;
  }
}
