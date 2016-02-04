package com.gigigo.orchextra.android.beacons.monitoring;

import com.gigigo.orchextra.android.beacons.RegionsProviderListener;
import com.gigigo.orchextra.android.beacons.actions.Action;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerController;
import com.gigigo.orchextra.android.beacons.model.OrchextraBeacon;
import com.gigigo.orchextra.android.beacons.model.OrchextraBeaconMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class BeaconsController {

  private final OrchextraBeaconMapper orchextraBeaconMapper;
  private final OrchextraRegionMapper orchextraRegionMapper;

  //TODO move action scheduler to Actions Interactor - controller
  private final ActionsSchedulerController actionsSchedulerController;

  public BeaconsController(OrchextraBeaconMapper orchextraBeaconMapper,
      OrchextraRegionMapper orchextraRegionMapper, ActionsSchedulerController actionsSchedulerController) {
    this.orchextraBeaconMapper = orchextraBeaconMapper;
    this.orchextraRegionMapper = orchextraRegionMapper;
    this.actionsSchedulerController = actionsSchedulerController;
  }

  public void getAllRegionsFromDataBase(RegionsProviderListener regionsProviderListener) {

  }

  public void onRegionExit(Region region) {
    //TODO region Interactor,
    // - delete enter previously stored

    // - Delete and cancel schedule if procced
    actionsSchedulerController.cancelPendingActionWithId(region.getUniqueId(), false);

    region.getUniqueId();
  }

  public void onRegionEnter(Region region) {
    //TODO region Interactor
    // - check if region was in enter
    // - if was in enter Log Error
    // - generate trigger
    //TODO actionsScheduler.addAction(); this method is internally managed by actions controller

    actionsSchedulerController.addAction(new Action());

    // - store enter
    region.getUniqueId();

    //Check if it is neccessary to schedule action
  }

  public void onBeaconsDetectedInRegion(Collection<Beacon> collection, Region region) {
    List<OrchextraBeacon> beaconList = mapBeaconsToBusinessObjects(collection);

  }

  private List<OrchextraBeacon> mapBeaconsToBusinessObjects(Collection<Beacon> collection) {
    List<OrchextraBeacon> beaconList = new ArrayList<>();
        for (Beacon beacon:collection){
          beaconList.add(orchextraBeaconMapper.map(beacon));
        }
    return beaconList;
  }
}
