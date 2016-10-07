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

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class RegionRealm extends RealmObject {

  public static final String CODE_FIELD_NAME = "code";

  @PrimaryKey @Required private String code;

  private String uuid;
  private int minor;
  private int major;
  private String eventType;
  private String actionRelated;
  private boolean active;
  private boolean actionRelatedCancelable;

  public RegionRealm(RegionRealm regionRealm) {
    this.code = regionRealm.getCode();
    this.uuid = regionRealm.getUuid();
    this.major = regionRealm.getMajor();
    this.minor = regionRealm.getMinor();
    this.eventType = regionRealm.getEventType();
    this.actionRelated = regionRealm.getActionRelated();
    this.actionRelatedCancelable = regionRealm.isActionRelatedCancelable();
    this.active = regionRealm.isActive();
  }

  public RegionRealm() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public int getMajor() {
    return major;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getActionRelated() {
    return actionRelated;
  }

  public void setActionRelated(String actionRelated) {
    this.actionRelated = actionRelated;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActionRelatedCancelable() {
    return actionRelatedCancelable;
  }

  public void setActionRelatedCancelable(boolean actionRelatedCancelable) {
    this.actionRelatedCancelable = actionRelatedCancelable;
  }
}