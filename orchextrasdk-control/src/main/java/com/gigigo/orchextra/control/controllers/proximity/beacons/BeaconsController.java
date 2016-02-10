package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.beacons.RegionsProviderListener;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconCheckerInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconTriggerInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.interactors.beacons.ObtainRegionsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionCheckerInteractor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final InteractorInvoker interactorInvoker;

  private final GetActionInteractor getActionInteractor;
  private final RegionCheckerInteractor regionCheckerInteractor;
  private final ActionDispatcher actionDispatcher;
  private final BeaconCheckerInteractor beaconCheckerInteractor;
  private final ObtainRegionsInteractor obtainRegionsInteractor;

  private final BeaconTriggerInteractor beaconTriggerInteractor;

  public BeaconsController(InteractorInvoker interactorInvoker, ActionDispatcher actionDispatcher,
      ObtainRegionsInteractor obtainRegionsInteractor,
      RegionCheckerInteractor regionCheckerInteractor,
      BeaconCheckerInteractor beaconCheckerInteractor,
      BeaconTriggerInteractor beaconTriggerInteractor,
      GetActionInteractor getActionInteractor) {

    this.interactorInvoker = interactorInvoker;
    this.actionDispatcher = actionDispatcher;

    this.obtainRegionsInteractor = obtainRegionsInteractor;
    this.regionCheckerInteractor = regionCheckerInteractor;
    this.beaconCheckerInteractor = beaconCheckerInteractor;
    this.beaconTriggerInteractor = beaconTriggerInteractor;
    this.getActionInteractor = getActionInteractor;
  }

  public void getAllRegionsFromDataBase(final RegionsProviderListener regionsProviderListener) {
    executeBeaconInteractor(obtainRegionsInteractor, new InteractorResult<List<OrchextraRegion>>() {
      @Override public void onResult(List<OrchextraRegion> regions) {
        regionsProviderListener.onRegionsReady(regions);
      }
    });
  }

  public void onBeaconsDetectedInRegion(List<OrchextraBeacon> beacons, OrchextraRegion region) {
    checkBeaconsEvents(beacons);
  }

  public void onRegionEnter(OrchextraRegion region) {
    checkRegionEvent(region, GeoPointEventType.ENTER);
  }

  public void onRegionExit(OrchextraRegion region) {
    checkRegionEvent(region, GeoPointEventType.EXIT);
  }

  private void checkBeaconsEvents(List<OrchextraBeacon> detectedBeacons) {
    beaconCheckerInteractor.setOrchextraBeacons(detectedBeacons);
    executeBeaconInteractor(beaconCheckerInteractor, new InteractorResult<List<OrchextraBeacon>>() {
      @Override public void onResult(List<OrchextraBeacon> beacons) {
          generateTriggerEvent(beacons);
      }
    });
  }

  private void checkRegionEvent(final OrchextraRegion detectedRegion, final GeoPointEventType geoPointEventType) {
    regionCheckerInteractor.setOrchextraRegion(detectedRegion);
    executeBeaconInteractor(regionCheckerInteractor, new InteractorResult<OrchextraRegion>() {
      @Override public void onResult(OrchextraRegion storedRegion) {
        if (geoPointEventType == GeoPointEventType.EXIT) {
          deleteScheduledActionIfExists(storedRegion);
        }
        generateTriggerEvent(detectedRegion);
      }
    });
  }

  private void generateTriggerEvent(OrchextraRegion detectedRegion) {
    //create interactor instance
    beaconTriggerInteractor.setOrchextraRegion(detectedRegion);
    generateTriggerEvent(beaconTriggerInteractor);
    // generate trigger
    // - if Action is scheduled inform region about action id then schedule
    // - if action is not scheduled execute
    // - if no action do nothing
    // - Modify enter event in db with scheduled action id
  }

  private void generateTriggerEvent(List<OrchextraBeacon> beaconList) {
    //create interactor instance
    beaconTriggerInteractor.setOrchextraBeacons(beaconList);
    generateTriggerEvent(beaconTriggerInteractor);
  }

  private void generateTriggerEvent(BeaconTriggerInteractor interactor) {
    executeBeaconInteractor(interactor, new InteractorResult<List<Trigger>>() {
      @Override public void onResult(List<Trigger> triggers) {
        executeActions(triggers);
      }
    });
  }

  private void executeActions(List<Trigger> triggers) {
    for (Trigger trigger : triggers) {
      executeAction(trigger);
    }
  }

  private void executeAction(Trigger trigger) {
    //create interactor instance
    getActionInteractor.setActionCriteria(trigger);

    executeBeaconInteractor(getActionInteractor, new InteractorResult<BasicAction>() {
      @Override public void onResult(BasicAction result) {
        result.performAction(actionDispatcher);
      }
    });

  }

  private void deleteScheduledActionIfExists(OrchextraRegion region) {
    if (region.hasActionRelated()){
      getActionInteractor.cancelPendingActionWithId(region.getActionRelated(), false);
    }
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
