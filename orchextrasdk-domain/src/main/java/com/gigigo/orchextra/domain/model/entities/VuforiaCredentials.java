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

package com.gigigo.orchextra.domain.model.entities;

public class VuforiaCredentials implements Updates {

  private String licenseKey;
  private String clientAccessKey;
  private String clientSecretKey;

  public String getLicenseKey() {
    return licenseKey;
  }

  public void setLicenseKey(String licenseKey) {
    this.licenseKey = licenseKey;
  }

  public String getClientAccessKey() {
    return clientAccessKey;
  }

  public void setClientAccessKey(String clientAccessKey) {
    this.clientAccessKey = clientAccessKey;
  }

  public String getClientSecretKey() {
    return clientSecretKey;
  }

  public void setClientSecretKey(String clientSecretKey) {
    this.clientSecretKey = clientSecretKey;
  }

  @Override
  public boolean hasChanges() {
    //by default, never change
    return false;
  }
}
