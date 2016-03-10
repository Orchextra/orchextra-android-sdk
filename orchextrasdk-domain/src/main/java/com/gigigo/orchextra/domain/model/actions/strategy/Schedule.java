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

package com.gigigo.orchextra.domain.model.actions.strategy;

public class Schedule {

  private final boolean cancelable;
  private final long scheduledTime;
  private String eventId;

  public Schedule(boolean cancelable, long scheduledTime) {
    this.cancelable = cancelable;
    this.scheduledTime = scheduledTime;
  }

  public boolean isCancelable() {
    return cancelable;
  }

  public long getScheduledTime() {
    return scheduledTime;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }
}
