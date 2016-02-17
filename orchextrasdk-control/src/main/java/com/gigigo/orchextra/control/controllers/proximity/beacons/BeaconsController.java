package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final InteractorInvoker interactorInvoker;
  private final ActionDispatcher actionDispatcher;
  private final Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider;
  private final Provider<InteractorExecution> regionsProviderInteractorExecutionProvider;

  public BeaconsController(InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      Provider<InteractorExecution> beaconsEventsInteractorExecutionProvider,
      Provider<InteractorExecution> regionsProviderInteractorExecutionProvider) {

    this.interactorInvoker = interactorInvoker;
    this.actionDispatcher = actionDispatcher;

    this.beaconsEventsInteractorExecutionProvider = beaconsEventsInteractorExecutionProvider;
    this.regionsProviderInteractorExecutionProvider = regionsProviderInteractorExecutionProvider;
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
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.BEACONS_DETECTED);
    dispatchBeaconEvent(beacons, execution, beaconEventsInteractor);
  }

  public void onRegionEnter(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.REGION_ENTER);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  public void onRegionExit(OrchextraRegion region) {
    InteractorExecution execution = beaconsEventsInteractorExecutionProvider.get();
    BeaconEventsInteractor beaconEventsInteractor = (BeaconEventsInteractor) execution.getInteractor();

    beaconEventsInteractor.setEventType(BeaconEventType.REGION_EXIT);
    dispatchBeaconEvent(region, execution, beaconEventsInteractor);
  }

  private void dispatchBeaconEvent(Object data, InteractorExecution interactorExecution, BeaconEventsInteractor beaconEventsInteractor) {
    //TODO beaconEventsInteractor get new instance
    beaconEventsInteractor.setData(data);
    executeBeaconInteractor(interactorExecution, new InteractorResult<List<BasicAction>>() {
      @Override public void onResult(List<BasicAction> actions) {
        for (BasicAction action:actions){
          action.performAction(actionDispatcher);
        }

      }
    });
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution, InteractorResult interactorResult) {
    interactorExecution.result(interactorResult)
        .error(InteractorError.class, new InteractorResult<InteractorError>() {
          @Override public void onResult(InteractorError result) {
            if (result instanceof BeaconsInteractorError) {
              BeaconsInteractorError beaconsInteractorError = (BeaconsInteractorError) result;
              manageBeaconInteractorError(beaconsInteractorError);
            } else {
              manageInteractorError(result);
            }
          }
        }).execute(interactorInvoker);
  }

  private void manageInteractorError(InteractorError result) {
    //TODO implement if neccessary
  }

  private void manageBeaconInteractorError(BeaconsInteractorError result) {

    switch (result.getBeaconBusinessErrorType()){
      case NO_SUCH_REGION_IN_ENTER:
        // TODO Log error about trying to make exit over not registered as entered Region
        break;
      case ALREADY_IN_ENTER_REGION:
        // TODO Log error about Region Was already in enter
        break;
      default:
        //TODO default error and log
        break;
    }
  }

}
