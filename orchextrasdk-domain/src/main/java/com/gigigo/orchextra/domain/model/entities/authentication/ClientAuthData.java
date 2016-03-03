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

package com.gigigo.orchextra.domain.model.entities.authentication;

import java.util.Calendar;
import java.util.Date;


public class ClientAuthData {

  private final String projectId;
  private final String userId;
  private final String value;
  private final int expiresIn;
  private final Date expiresAt;

  public ClientAuthData(String projectId, String userId, String value, int expiresIn) {
    this.projectId = projectId;
    this.userId = userId;
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = calculateExpireTime(expiresIn);
  }

  public ClientAuthData(String projectId, String userId, String value, int expiresIn,
      Date expiresAt) {
    this.projectId = projectId;
    this.userId = userId;
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = expiresAt;
  }

  private Date calculateExpireTime(int expiresIn) {
    return new Date(Calendar.getInstance().getTimeInMillis() + expiresIn);
  }

  public String getProjectId() {
    return projectId;
  }

  public String getUserId() {
    return userId;
  }

  public String getValue() {
    return value;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public boolean isExpired() {
    return Calendar.getInstance().getTime().after(expiresAt);
  }
}
