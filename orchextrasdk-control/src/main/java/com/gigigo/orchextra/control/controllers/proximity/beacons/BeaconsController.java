package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.domain.entities.OrchextraBeacon;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import java.util.Collection;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final GetActionInteractor getActionInteractor;

  public BeaconsController(GetActionInteractor getActionInteractor) {
    this.getActionInteractor = getActionInteractor;
  }

  public void getAllRegionsFromDataBase(RegionsProviderListener regionsProviderListener) {

  }

  public void onRegionExit(OrchextraRegion region) {
    //TODO region Interactor,
    // - delete enter previously stored and recover
    // - Check if enter had any action associated
    // - if action asociated cancel
    // - Delete and cancel schedule if procced

    if (region.hasActionRelated()){
      getActionInteractor.cancelPendingActionWithId(region.getActionRelated(), false);
    }


    region.getCode();
  }

  public void onRegionEnter(OrchextraRegion region) {
    //TODO region Interactor
    // - check if region was in enter
    // - if was in enter Log Error
    // - else generate trigger
    // - if Action is scheduled inform region about action id then schedule
    // - if action is not scheduled execute
    // - if no action do nothing
    // - Store enter event in db

  }

  public void onBeaconsDetectedInRegion(Collection<OrchextraBeacon> collection, OrchextraRegion region) {
    //TODO

  }

}
