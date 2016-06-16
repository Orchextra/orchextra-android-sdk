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

package com.gigigo.orchextra.control.controllers.proximity.geofence;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.geofences.GeofencesProviderListener;
import com.gigigo.orchextra.domain.abstractions.threads.ThreadSpec;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveGeofenceItemError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;

import java.util.List;

import orchextra.javax.inject.Provider;

public class GeofenceController {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> interactorExecutionProvider;
  private final Provider<InteractorExecution> geofencesProviderInteractorExecutionProvider;
  private final ActionDispatcher actionDispatcher;
  private final ErrorLogger errorLogger;
  private final ThreadSpec mainThreadSpec;

  public GeofenceController(InteractorInvoker interactorInvoker,
                            Provider<InteractorExecution> interactorExecutionProvider,
                            Provider<InteractorExecution> geofencesProviderInteractorExecutionProvider,
                            ActionDispatcher actionDispatcher,
                            ErrorLogger errorLogger,
                            ThreadSpec mainThreadSpec) {
    this.interactorInvoker = interactorInvoker;
    this.interactorExecutionProvider = interactorExecutionProvider;
    this.geofencesProviderInteractorExecutionProvider = geofencesProviderInteractorExecutionProvider;
    this.actionDispatcher = actionDispatcher;
    this.errorLogger = errorLogger;
    this.mainThreadSpec = mainThreadSpec;
  }

  public void processTriggers(List<String> triggeringGeofenceIds,
      GeoPointEventType geofenceTransition) {

    InteractorExecution interactorExecution = interactorExecutionProvider.get();
    GeofenceInteractor geofenceInteractor =
        (GeofenceInteractor) interactorExecution.getInteractor();
    geofenceInteractor.setGeofenceData(triggeringGeofenceIds, geofenceTransition);

    interactorExecution.result(new InteractorResult<List<BasicAction>>() {
      @Override public void onResult(List<BasicAction> actions) {
        executeActions(actions);
      }
    }).error(RetrieveGeofenceItemError.class, new InteractorResult<RetrieveGeofenceItemError>() {
      @Override public void onResult(RetrieveGeofenceItemError result) {
        errorLogger.log(result.getError());
      }
    }).execute(interactorInvoker);
  }

  private void executeActions(List<BasicAction> actions) {

    for (final BasicAction action : actions) {
      mainThreadSpec.execute(new Runnable() {
        @Override public void run() {
          action.performAction(actionDispatcher);
        }
      });
    }
  }

  public void getAllGeofencesInDb(final GeofencesProviderListener geofencesProviderListener) {

    InteractorExecution interactorExecution = geofencesProviderInteractorExecutionProvider.get();

    interactorExecution.result(new InteractorResult<List<OrchextraGeofence>>() {
      @Override public void onResult(List<OrchextraGeofence> geofences) {
        geofencesProviderListener.onGeofencesReady(geofences);
      }
    }).error(RetrieveGeofenceItemError.class, new InteractorResult<RetrieveGeofenceItemError>() {
      @Override public void onResult(RetrieveGeofenceItemError result) {
        errorLogger.log(result.getError());
      }
    }).execute(interactorInvoker);


  }
}
