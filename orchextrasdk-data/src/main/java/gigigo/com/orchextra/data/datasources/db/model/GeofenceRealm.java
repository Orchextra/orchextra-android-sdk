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

package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class GeofenceRealm extends RealmObject {

  @PrimaryKey private String code;
  private RealmPoint point;
  private int radius;
  private boolean notifyOnExit;
  private boolean notifyOnEntry;
  private int stayTime;

  private String id;
  private String name;
  private String type;
  private String createdAt;
  private String updatedAt;

  public RealmPoint getPoint() {
    return point;
  }

  public void setPoint(RealmPoint point) {
    this.point = point;
  }

  public int getRadius() {
    return radius;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean getNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }

  public boolean getNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }

  public int getStayTime() {
    return stayTime;
  }

  public void setStayTime(int stayTime) {
    this.stayTime = stayTime;
  }
}
