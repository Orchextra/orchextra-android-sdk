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

package com.gigigo.orchextra.delegates;

import com.gigigo.orchextra.control.controllers.authentication.SaveCrmUserController;
import com.gigigo.orchextra.control.controllers.authentication.SaveCrmUserDelegate;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;

public class SaveCrmUserDelegateImpl implements SaveCrmUserDelegate {

  private final SaveCrmUserController saveCrmUserController;
  private final OrchextraLogger orchextraLogger;

  public SaveCrmUserDelegateImpl(SaveCrmUserController saveCrmUserController, OrchextraLogger orchextraLogger) {
    this.saveCrmUserController = saveCrmUserController;
    this.orchextraLogger = orchextraLogger;
  }

  @Override public void init() {
    saveCrmUserController.attachView(this);
  }

  @Override public void destroy() {
    saveCrmUserController.detachView();
  }

  @Override public void saveCrmUserSuccessful() {
    destroy();
  }

  @Override public void saveCrmUserError() {
    orchextraLogger.log("Save user was not successful", OrchextraSDKLogLevel.ERROR);
    destroy();
  }

  @Override
  public void saveCrmUser(CrmUser crmUser) {
    init();
    saveCrmUserController.saveUserAndReloadConfig(crmUser);
  }

}
