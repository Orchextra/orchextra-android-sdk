package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
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

    proximityLocalDataProvider.purgeOldBeaconEventsWithRequestTime(requestTime);

    return obtainTriggerableBeacons(orchextraBeacons);
  }

  private InteractorResponse obtainTriggerableBeacons(List<OrchextraBeacon> orchextraBeacons) {

    BusinessObject<List<OrchextraBeacon>> bo = proximityLocalDataProvider.getNotStoredBeaconEvents(orchextraBeacons);

    List<OrchextraBeacon> triggerableBeacons = bo.getData();

    for (OrchextraBeacon beacon:triggerableBeacons){
        proximityLocalDataProvider.storeBeaconEvent(beacon);
    }

    return new InteractorResponse<>(triggerableBeacons);
  }
}
