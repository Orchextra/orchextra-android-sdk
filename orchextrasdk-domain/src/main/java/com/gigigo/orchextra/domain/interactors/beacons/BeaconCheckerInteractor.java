package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class BeaconCheckerInteractor  implements Interactor<InteractorResponse<List<OrchextraBeacon>>> {

  private final ProximityLocalDataProvider proximityLocalDataProvider;
  private List<OrchextraBeacon> orchextraBeacons;
  private int requestTime;

  public BeaconCheckerInteractor(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public void setRequestTime(int requestTime) {
    this.requestTime = requestTime;
  }

  public void setOrchextraBeacons(List<OrchextraBeacon> orchextraBeacons) {
    this.orchextraBeacons = orchextraBeacons;
  }

  @Override
  public InteractorResponse<List<OrchextraBeacon>> call() throws Exception {

    boolean empty = proximityLocalDataProvider.purgeOldBeaconEventsWithRequestTime(orchextraBeacons, requestTime);

    if (empty){
      return new InteractorResponse<>(orchextraBeacons);
    }else{
      return obtainTriggerableBeacons(orchextraBeacons);
    }
  }

  private InteractorResponse obtainTriggerableBeacons(List<OrchextraBeacon> orchextraBeacons) {

    List<OrchextraBeacon> triggerBeacons = new ArrayList<>();

    for (OrchextraBeacon beacon:orchextraBeacons){
      boolean beaconEventRegistered = proximityLocalDataProvider.isBeaconEventStored(beacon);
      if (!beaconEventRegistered){
        proximityLocalDataProvider.storeBeaconEvent(beacon);
        triggerBeacons.add(beacon);
      }
    }

    return new InteractorResponse<>(triggerBeacons);

  }
}
