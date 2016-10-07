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
package com.gigigo.orchextra.domain.abstractions.initialization;

public interface OrchextraStatusAccessor {

  void initialize();

  /**
   * Checks sdk status, and sets started status if its able, when finished returns current status,
   * possible return status types can be StartStatusType.SDK_READY_FOR_START and
   * StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS, other status currently are
   * throwing RuntimeException because nothing relevant should be done if they are returned.
   *
   * @param apiKey credentials
   * @param apiSecret credentials
   * @return StartStatusType.SDK_READY_FOR_START in case of having to start sdk and
   * StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS in case of having to launch
   * again authentication.
   * @throws RuntimeException
   */
  StartStatusType getOrchextraStatusWhenReinitMode(String apiKey, String apiSecret) throws RuntimeException;

  StartStatusType getOrchextraStatusWhenStartMode() throws RuntimeException;

  boolean hasCredentials();

  /**
   * Checks if sdk status is already started
   *
   * @return boolean: true if started and false otherwise
   */
  boolean isStarted();

  /**
   * sets sdk status as stopped
   */
  void setStoppedStatus();

  void saveCredentials(String apiKey, String apiSecret);

  //region TODO check com.gigigo.orchextra.domain.model.vo.OrchextraStatus
  //TODO move CRM and Session management here
  //Session getSession();
  //void updateSession(Session session);

  //CrmUser getCrmUser();
  //void updateCrm(CrmUser crm);
  //endregion
}
