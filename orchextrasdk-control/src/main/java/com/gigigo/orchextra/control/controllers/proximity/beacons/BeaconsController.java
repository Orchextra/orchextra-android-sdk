package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final InteractorInvoker interactorInvoker;
  private final ActionDispatcher actionDispatcher;
  private final BeaconEventsInteractor beaconEventsInteractor;
  private final RegionsProviderInteractor regionsProviderInteractor;

  public BeaconsController(InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      BeaconEventsInteractor beaconEventsInteractor,
      RegionsProviderInteractor regionsProviderInteractor) {

    this.interactorInvoker = interactorInvoker;
    this.actionDispatcher = actionDispatcher;

    this.beaconEventsInteractor = beaconEventsInteractor;
    this.regionsProviderInteractor = regionsProviderInteractor;
  }

  public void getAllRegionsFromDataBase(final RegionsProviderListener regionsProviderListener) {
    executeBeaconInteractor(regionsProviderInteractor, new InteractorResult<List<OrchextraRegion>>() {
      @Override public void onResult(List<OrchextraRegion> regions) {
        regionsProviderListener.onRegionsReady(regions);
      }
    });
  }

  public void onBeaconsDetectedInRegion(List<OrchextraBeacon> beacons, OrchextraRegion region) {
    //TODO beaconEventsInteractor get new instance
    beaconEventsInteractor.setEventType(BeaconEventType.BEACONS_DETECTED);
    dispatchBeaconEvent(beacons);
  }

  public void onRegionEnter(OrchextraRegion region) {
    //TODO beaconEventsInteractor get new instance
    beaconEventsInteractor.setEventType(BeaconEventType.REGION_ENTER);
    dispatchBeaconEvent(region);
  }

  public void onRegionExit(OrchextraRegion region) {
    //TODO beaconEventsInteractor get new instance
    beaconEventsInteractor.setEventType(BeaconEventType.REGION_EXIT);
    dispatchBeaconEvent(region);
  }

  private void dispatchBeaconEvent(Object data) {
    //TODO beaconEventsInteractor get new instance
    beaconEventsInteractor.setData(data);
    executeBeaconInteractor(beaconEventsInteractor, new InteractorResult<List<BasicAction>>() {
      @Override public void onResult(List<BasicAction> actions) {
        for (BasicAction action:actions){
          action.performAction(actionDispatcher);
        }

      }
    });
  }

  private void executeBeaconInteractor(Interactor interactor, InteractorResult interactorResult) {
    new InteractorExecution<>(interactor).result(interactorResult)
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
