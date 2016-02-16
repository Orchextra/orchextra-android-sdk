package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class BeaconCheckerService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;
  private final ConfigDataProvider configDataProvider;

  public BeaconCheckerService(ProximityLocalDataProvider proximityLocalDataProvider,
      ConfigDataProvider configDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
    this.configDataProvider = configDataProvider;
  }

  public InteractorResponse getCheckedBeaconList(List<OrchextraBeacon> orchextraBeacons) {

    int requestTime = configDataProvider.obtainRequestTime();

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
