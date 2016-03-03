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

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class SdkAuthData {

  private final String value;
  private final int expiresIn;
  private final Date expiresAt;
  private final String projectId;

  public SdkAuthData(String value, int expiresIn, String projectId) {
    this.value = value;
    this.expiresIn = expiresIn;
    this.projectId = projectId;
    this.expiresAt = calculateExpireTime(expiresIn);
  }

  public SdkAuthData(String value, int expiresIn, Date expiresAt, String projectId) {
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = expiresAt;
    this.projectId = projectId;
  }

  private Date calculateExpireTime(int expiresIn) {
    return new Date(Calendar.getInstance().getTimeInMillis() + expiresIn);
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

  public String getProjectId() {
    return projectId;
  }

  public boolean isExpired() {
    return Calendar.getInstance().getTime().after(expiresAt);
  }
}
