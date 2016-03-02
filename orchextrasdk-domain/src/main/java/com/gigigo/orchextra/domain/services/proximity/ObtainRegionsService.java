package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class ObtainRegionsService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public ObtainRegionsService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse obtainRegionsFromLocalStorage() throws Exception {

    BusinessObject<List<OrchextraRegion>> bo = proximityLocalDataProvider.getBeaconRegionsForScan();

    if (bo.isSuccess()) {
      return new InteractorResponse<>(bo.getData());
    } else {
      return new InteractorResponse<>(Collections.<OrchextraRegion>emptyList());
    }
  }
}
