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

package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrchextraApiConfigRequest {

  @Expose @SerializedName("app") private ApiApp app;

  @Expose @SerializedName("device") private ApiDevice device;

  @Expose @SerializedName("geoLocation") private ApiGeoLocation geoLocation;

  @Expose @SerializedName("notificationPush") private ApiNotificationPush notificationPush;

  @Expose @SerializedName("crm") private ApiCrm crm;

  public ApiApp getApp() {
    return app;
  }

  public void setApp(ApiApp app) {
    this.app = app;
  }

  public ApiDevice getDevice() {
    return device;
  }

  public void setDevice(ApiDevice device) {
    this.device = device;
  }

  public ApiGeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(ApiGeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }

  public ApiNotificationPush getNotificationPush() {
    return notificationPush;
  }

  public void setNotificationPush(ApiNotificationPush notificationPush) {
    this.notificationPush = notificationPush;
  }

  public ApiCrm getCrm() {
    return crm;
  }

  public void setCrm(ApiCrm crm) {
    this.crm = crm;
  }
}
