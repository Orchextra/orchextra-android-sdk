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

package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.services.DomaninService;


public class EventUpdaterService implements DomaninService {

  private final ProximityLocalDataProvider proximityLocalDataProvider;

  public EventUpdaterService(ProximityLocalDataProvider proximityLocalDataProvider) {
    this.proximityLocalDataProvider = proximityLocalDataProvider;
  }

  public InteractorResponse associateActionToRegionEvent(OrchextraRegion orchextraRegion) {

    BusinessObject<OrchextraRegion> bo =
        proximityLocalDataProvider.updateRegionWithActionId(orchextraRegion);

    if (bo.isSuccess()) {
      return new InteractorResponse(bo.getData());
    } else {
      return new InteractorResponse(false);
    }
  }

  public InteractorResponse associateActionToGeofenceEvent(OrchextraGeofence geofence) {
    BusinessObject<OrchextraGeofence> bo =
        proximityLocalDataProvider.updateGeofenceWithActionId(geofence);

    if (bo.isSuccess()) {
      return new InteractorResponse(bo.getData());
    } else {
      return new InteractorResponse(false);
    }
  }
}