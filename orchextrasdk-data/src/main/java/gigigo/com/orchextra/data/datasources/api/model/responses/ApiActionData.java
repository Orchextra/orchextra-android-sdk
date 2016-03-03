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

package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiActionData {

  @Expose @SerializedName("trackId") private String trackId;

  @Expose @SerializedName("id") private String id;

  @Expose @SerializedName("type") private String type;

  @Expose @SerializedName("url") private String url;

  @Expose @SerializedName("notification") private ApiNotification notification;

  @Expose @SerializedName("schedule") private ApiSchedule schedule;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ApiNotification getNotification() {
    return notification;
  }

  public void setNotification(ApiNotification notification) {
    this.notification = notification;
  }

  public ApiSchedule getSchedule() {
    return schedule;
  }

  public void ApiSchedule(ApiSchedule schedule) {
    this.schedule = schedule;
  }

  public String getTrackId() {
    return trackId;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
