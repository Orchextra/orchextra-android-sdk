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
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
import com.gigigo.orchextra.domain.services.config.ConfigService;


public class SaveUserInteractor implements Interactor<InteractorResponse<OrchextraUpdates>> {

  private final AuthenticationService authenticationService;
  private final ConfigService configService;

  private Crm crm;

  public SaveUserInteractor(AuthenticationService authenticationService,
      ConfigService configService) {

    this.authenticationService = authenticationService;
    this.configService = configService;
  }

  public void setCrm(Crm crm) {
    this.crm = crm;
  }

  @Override public InteractorResponse<OrchextraUpdates> call() {
    InteractorResponse<OrchextraUpdates> boOrchextraUpdates;

    BusinessObject<Crm> crmBusinessObject = authenticationService.saveUser(crm);

    if (crmBusinessObject.isSuccess()) {
      boOrchextraUpdates = configService.refreshConfig();
    } else {
      return new InteractorResponse<OrchextraUpdates>(
          new AuthenticationError(crmBusinessObject.getBusinessError()));
    }

    return boOrchextraUpdates;
  }
}
