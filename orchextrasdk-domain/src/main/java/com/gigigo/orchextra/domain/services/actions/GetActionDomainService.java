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
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.services.DomainService;

import java.util.ArrayList;
import java.util.List;

public class GetActionDomainService implements DomainService {

    private final ActionsDataProvider actionsDataProvider;
    private final ServiceErrorChecker serviceErrorChecker;
    private final AppRunningMode appRunningMode;

    public GetActionDomainService(ActionsDataProvider actionsDataProvider,
                                  ServiceErrorChecker serviceErrorChecker, AppRunningMode appRunningMode) {

        this.actionsDataProvider = actionsDataProvider;
        this.serviceErrorChecker = serviceErrorChecker;
        this.appRunningMode = appRunningMode;
    }

    private InteractorResponse<List<BasicAction>> getActions(List<Trigger> triggers, int numRetries) {
        List<BasicAction> actions = new ArrayList<>();

        BusinessObject<BasicAction> boBasicAction = null;

        for (Trigger actionCriteria : triggers) {
            boBasicAction = actionsDataProvider.obtainAction(actionCriteria);

            if (boBasicAction.isSuccess()) {
                BasicAction basicAction = (boBasicAction.getData() != null) ? boBasicAction.getData() : new EmptyAction();
                basicAction.setEventCode(actionCriteria.getCode());
                if (appRunningMode.getRunningModeType() == AppRunningModeType.BACKGROUND
                        && !basicAction.getHasNotification()) {
                    basicAction.createFakeNotification();
                }
                actions.add(basicAction);
            } else {
                break;
            }
        }

        if (boBasicAction != null && !boBasicAction.isSuccess()) {
            boolean retry = manageError(boBasicAction.getBusinessError());
            if (retry && numRetries <= 3) {
                return getActions(triggers, numRetries + 1);
            }
        }


        return new InteractorResponse<>(actions);
    }

    public InteractorResponse<List<BasicAction>> getActions(List<Trigger> triggers) {
        return getActions(triggers, 0);
    }

    private boolean manageError(BusinessError businessError) {
        InteractorResponse response = serviceErrorChecker.checkErrors(businessError);
        return !response.hasError();
    }
}
