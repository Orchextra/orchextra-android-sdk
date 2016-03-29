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

package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.ArrayList;
import java.util.List;

public class GetActionService implements DomaninService {

  private final ActionsDataProvider actionsDataProvider;
  private final ServiceErrorChecker serviceErrorChecker;

  public GetActionService(ActionsDataProvider actionsDataProvider,
      ServiceErrorChecker serviceErrorChecker) {

    this.actionsDataProvider = actionsDataProvider;
    this.serviceErrorChecker = serviceErrorChecker;
  }

  private InteractorResponse getActions(List<Trigger> triggers, int numRetries) {
    List<BasicAction> actions = new ArrayList<>();

    BusinessObject<BasicAction> bo = null;

    for (Trigger actionCriteria : triggers) {
      bo = actionsDataProvider.obtainAction(actionCriteria);

      if (bo.isSuccess()) {
        BasicAction basicAction = (bo.getData() != null) ? bo.getData() : new EmptyAction();
        basicAction.setEventCode(actionCriteria.getCode());
        actions.add(basicAction);
      } else {
        break;
      }
    }

    if (bo != null && !bo.isSuccess()) {
      boolean retry = manageError(bo.getBusinessError());
      if (retry && numRetries <= 3) {
        return getActions(triggers, numRetries + 1);
      }
    }

    return new InteractorResponse<>(actions);
  }

  public InteractorResponse getActions(List<Trigger> triggers) {
    return getActions(triggers, 0);
  }

  private boolean manageError(BusinessError businessError) {
    InteractorResponse response = serviceErrorChecker.checkErrors(businessError);
    return !response.hasError();
  }
}
