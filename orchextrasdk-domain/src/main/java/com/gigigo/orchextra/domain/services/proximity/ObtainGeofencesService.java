package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/4/16.
 */
public class ObtainGeofencesService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public ObtainGeofencesService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse obtainGeofencesFromLocalStorage() throws Exception {

    BusinessObject<List<OrchextraGeofence>> bo = proximityLocalDataProvider.obtainGeofencesForRegister();

    if (bo.isSuccess()) {
      return new InteractorResponse<>(bo.getData());
    } else {
      return new InteractorResponse<>(Collections.<OrchextraRegion>emptyList());
    }
  }
}
