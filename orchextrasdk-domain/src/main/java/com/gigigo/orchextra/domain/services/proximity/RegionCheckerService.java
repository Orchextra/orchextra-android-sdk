/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconBusinessErrorType;
import com.gigigo.orchextra.domain.interactors.beacons.BeaconsInteractorError;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;

public class RegionCheckerService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public RegionCheckerService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse obtainCheckedRegion(OrchextraRegion orchextraRegion) {
    if (orchextraRegion.isEnter()) {
      return storeRegion(orchextraRegion);
    } else {
      return deleteStoredRegion(orchextraRegion);
    }
  }

  private InteractorResponse deleteStoredRegion(OrchextraRegion orchextraRegion) {
    BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.deleteRegion(orchextraRegion);
    if (!bo.isSuccess()) {
      return new InteractorResponse(
          new BeaconsInteractorError(BeaconBusinessErrorType.NO_SUCH_REGION_IN_ENTER));
    } else {
      return new InteractorResponse(bo.getData());
    }
  }

  private InteractorResponse storeRegion(OrchextraRegion orchextraRegion) {
    BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.obtainRegion(orchextraRegion);
    if (bo.isSuccess()) {
      return new InteractorResponse(
          new BeaconsInteractorError(BeaconBusinessErrorType.ALREADY_IN_ENTER_REGION));
    } else {
      bo = proximityLocalDataProvider.storeRegion(orchextraRegion);
      return new InteractorResponse(bo.getData());
    }
  }
}
