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

package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.interactors.beacons.ProximityEventType;
import com.gigigo.orchextra.domain.model.ScheduledActionEvent;

public class OrchextraRegion extends ProximityItem implements ScheduledActionEvent {

  private final String code;
  private final String uuid;
  private final int minor;
  private final int major;
  private RegionEventType regionEvent;
  private ActionRelatedWithRegionAndGeofences actionRelatedWithRegionAndGeofences;
  private final boolean active;

  public OrchextraRegion(String code, String uuid, int major, int minor, boolean active) {
    this.code = code;
    this.uuid = uuid;
    this.minor = minor;
    this.major = major;
    this.active = active;
  }

  public String getCode() {
    return code;
  }

  public String getUuid() {
    return uuid;
  }

  public int getMinor() {
    return minor;
  }

  public int getMajor() {
    return major;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isEnter() {
    return regionEvent == RegionEventType.ENTER;
  }

  public boolean isExit() {
    return regionEvent == RegionEventType.EXIT;
  }

  public RegionEventType getRegionEvent() {
    return regionEvent;
  }

  public void setRegionEvent(RegionEventType regionEvent) {
    this.regionEvent = regionEvent;
  }

  public void setRegionEvent(ProximityEventType eventType) {
    if (eventType == ProximityEventType.REGION_ENTER) {
      regionEvent = RegionEventType.ENTER;
    } else {
      regionEvent = RegionEventType.EXIT;
    }
  }

  public void setActionRelatedWithRegionAndGeofences(ActionRelatedWithRegionAndGeofences actionRelatedWithRegionAndGeofences) {
    this.actionRelatedWithRegionAndGeofences = actionRelatedWithRegionAndGeofences;
  }

  @Override public String getActionRelatedId() {
    if (actionRelatedWithRegionAndGeofences == null) {
      return "";
    } else {
      return actionRelatedWithRegionAndGeofences.getActionId();
    }
  }

  @Override public boolean hasActionRelated() {
    return (actionRelatedWithRegionAndGeofences != null);
  }

  @Override public boolean relatedActionIsCancelable() {
    if (actionRelatedWithRegionAndGeofences == null) {
      return false;
    } else {
      return actionRelatedWithRegionAndGeofences.isCancelable();
    }
  }
}
