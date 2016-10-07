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

package com.gigigo.orchextra.domain.model.vo;

import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;

/**
 * This class represent the sdk status, store session and CrmUser
 */
public class OrchextraStatus {

  private boolean initialized;
  private boolean started;
  private CrmUser crmUser;
  private Session session;

  public boolean isInitialized() {
    return initialized;
  }

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public boolean isStarted() {
    return started;
  }

  public void setStarted(boolean started) {
    this.started = started;
  }

  public CrmUser getCrmUser() {
    return crmUser;
  }

  public void setCrmUser(CrmUser crmUser) {
    this.crmUser = crmUser;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public static OrchextraStatus getInstance() {
    return new OrchextraStatus();
  }
}
