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

package com.gigigo.orchextra.domain.services.status;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.OrchextraStatusDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.services.DomaninService;

public class UpdateOrchextraServiceStatus implements DomaninService {

  private final OrchextraStatusDataProvider orchextraStatusDataProvider;

  //TODO move Crm and Session info update here

  public UpdateOrchextraServiceStatus(OrchextraStatusDataProvider orchextraStatusDataProvider) {
    this.orchextraStatusDataProvider = orchextraStatusDataProvider;
  }

  public InteractorResponse<OrchextraStatus> update(OrchextraStatus orchextraStatus) {
    BusinessObject<OrchextraStatus> bo =
        orchextraStatusDataProvider.updateOrchextraStatus(orchextraStatus);

    if (!bo.isSuccess()) {
      return new InteractorResponse(new GenericError(bo.getBusinessError()));
    }

    return new InteractorResponse<OrchextraStatus>(bo.getData());
  }
}
