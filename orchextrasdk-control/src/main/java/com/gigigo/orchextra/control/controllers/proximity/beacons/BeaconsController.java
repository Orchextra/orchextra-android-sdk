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

package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.threads.ThreadSpec;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.ProximityEventType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.List;

import orchextra.javax.inject.Provider;

public class BeaconsController {

  private final InteractorInvoker interactorInvoker;
  private final ActionDispatcher actionDispatcher;
  private final Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider;
  private final Provider<InteractorExecution> regionsProviderInteractorExecutionProvider;
  private final ErrorLogger errorLogger;
  private final ThreadSpec mainThreadSpec;

  public BeaconsController(InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider,
      Provider<InteractorExecution> regionsProviderInteractorExecutionProvider,
      ErrorLogger errorLogger, ThreadSpec mainThreadSpec) {

    this.interactorInvoker = interactorInvoker;
    this.actionDispatcher = actionDispatcher;

    this.beaconsEventsInteractorExecutionProvider = beaconsEventsInteractorExecutionProvider;

    this.regionsProviderInteractorExecutionProvider = regionsProviderInteractorExecutionProvider;
    this.errorLogger = errorLogger;
    this.mainThreadSpec = mainThreadSpec;
  }

  public void getAllRegionsFromDataBase(final RegionsProviderListener regionsProviderListener) {
    executeBeaconInteractor(regionsProviderInteractorExecutionProvider.get(),
        new InteractorResult<List<OrchextraRegion>>() {
          @Override public void onResult(List<OrchextraRegion> regions) {
            regionsProviderListener.onRegionsReady(regions);
          }
        });
  }

  public void onBeaconsDetectedInRegion(List<OrchextraBeacon> beacons, OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor =
        (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(ProximityEventType.BEACONS_DETECTED);
    dispatchBeaconEvent(beacons, execution, beaconEventsInteractor);
  }

  public void onRegionEnter(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor =
        (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(ProximityEventType.REGION_ENTER);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  public void onRegionExit(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor =
        (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(ProximityEventType.REGION_EXIT);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  private void dispatchBeaconEvent(Object data, InteractorExecution interactorExecution,
      BeaconEventsInteractor beaconEventsInteractor) {
    beaconEventsInteractor.setData(data);
    executeBeaconInteractor(interactorExecution, new InteractorResult<List<BasicAction>>() {
      @Override public void onResult(List<BasicAction> actions) {
        for (final BasicAction action : actions) {
          mainThreadSpec.execute(new Runnable() {
            @Override public void run() {
              action.performAction(actionDispatcher);
            }
          });
        }
      }
    });
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution,
      InteractorResult interactorResult) {
    interactorExecution.result(interactorResult)
        .error(BeaconsInteractorError.class, new InteractorResult<BeaconsInteractorError>() {
          @Override public void onResult(BeaconsInteractorError result) {
            manageBeaconInteractorError(result);
          }
        })
        .error(InteractorError.class, new InteractorResult<InteractorError>() {
          @Override public void onResult(InteractorError result) {
            manageInteractorError(result);
          }
        })
        .execute(interactorInvoker);
  }

  private void manageInteractorError(InteractorError result) {
    errorLogger.log(result.getError());
  }

  private void manageBeaconInteractorError(BeaconsInteractorError result) {

    switch (result.getBeaconBusinessErrorType()) {
      case NO_SUCH_REGION_IN_ENTER:
        errorLogger.log(result.getError());
        break;
      case ALREADY_IN_ENTER_REGION:
        errorLogger.log(result.getError());
        break;
      default:
        errorLogger.log(result.getError());
        break;
    }
  }
}
