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

package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusManager;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
import com.gigigo.orchextra.domain.services.config.ConfigDomainService;

public class SaveUserInteractor implements Interactor<InteractorResponse<OrchextraUpdates>> {

  private final AuthenticationService authenticationService;
  private final ConfigDomainService configDomainService;
  private final OrchextraStatusManager orchextraStatusManager;

  private CrmUser crmUser;
  private boolean hasReloadConfig = false;

  public SaveUserInteractor(AuthenticationService authenticationService,
                            ConfigDomainService configDomainService,
                            OrchextraStatusManager orchextraStatusManager) {

    this.authenticationService = authenticationService;
    this.configDomainService = configDomainService;
    this.orchextraStatusManager = orchextraStatusManager;
  }

  public void setCrmUser(CrmUser crmUser) {
    this.crmUser = crmUser;
  }

  @Override public InteractorResponse<OrchextraUpdates> call() {
    InteractorResponse<OrchextraUpdates> boOrchextraUpdates;

    BusinessObject<CrmUser> crmBusinessObject = authenticationService.saveUser(crmUser);

    if (crmBusinessObject.isSuccess()) {
      if (hasReloadConfig && orchextraStatusManager.isStarted()) {
        boOrchextraUpdates = configDomainService.refreshConfig();
      } else {
        return new InteractorResponse<>(new OrchextraUpdates());
      }
    } else {
      return new InteractorResponse<>(
          new AuthenticationError(crmBusinessObject.getBusinessError()));
    }

    return boOrchextraUpdates;
  }

  public void setHasReloadConfig(boolean hasReloadConfig) {
    this.hasReloadConfig = hasReloadConfig;
  }
}
