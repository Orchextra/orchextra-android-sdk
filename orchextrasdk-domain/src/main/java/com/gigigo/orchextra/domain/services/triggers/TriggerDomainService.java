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

package com.gigigo.orchextra.domain.services.triggers;

import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.services.DomainService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriggerDomainService implements DomainService {

  private final AppRunningMode appRunningMode;

  public TriggerDomainService(AppRunningMode appRunningMode) {
    this.appRunningMode = appRunningMode;
  }

  public InteractorResponse getTrigger(OrchextraRegion orchextraRegion) {

    List<Trigger> triggers = Arrays.asList(
        Trigger.createBeaconRegionTrigger(appRunningMode.getRunningModeType(), orchextraRegion));

    return new InteractorResponse(triggers);
  }

  public InteractorResponse getTrigger(List<OrchextraBeacon> orchextraBeacons) {
    return createTriggersForBeacons(orchextraBeacons);
  }

  private InteractorResponse createTriggersForBeacons(List<OrchextraBeacon> orchextraBeacons) {

    List<Trigger> triggers = new ArrayList<>();

    for (OrchextraBeacon orchextraBeacon : orchextraBeacons) {
      Trigger trigger =
          Trigger.createBeaconTrigger(appRunningMode.getRunningModeType(), orchextraBeacon);
      triggers.add(trigger);
    }

    return new InteractorResponse(triggers);
  }

  public InteractorResponse getTrigger(List<OrchextraGeofence> geofences,
      GeoPointEventType geofenceTransition) {
    List<Trigger> triggers = new ArrayList<>();

    for (OrchextraGeofence orchextraGeofence : geofences) {

      Trigger trigger =
          Trigger.createGeofenceTrigger(orchextraGeofence.getCode(), orchextraGeofence.getPoint(),
                  appRunningMode.getRunningModeType(), orchextraGeofence.getDistanceToDeviceInKm(),
                  geofenceTransition);

      triggers.add(trigger);
    }

    return new InteractorResponse(triggers);
  }

  public InteractorResponse getTrigger(ScannerResult scanner, OrchextraPoint orchextraPoint) {
    List<Trigger> triggers = new ArrayList<>();

    switch (scanner.getType()) {
      case IMAGE_RECOGNITION:
        triggers.add(Trigger.createImageRecognitionTrigger(scanner.getContent(), orchextraPoint));
        break;
      case QRCODE:
        triggers.add(Trigger.createQrScanTrigger(scanner.getContent(), orchextraPoint));
        break;
      default:
        triggers.add(Trigger.createBarcodeScanTrigger(scanner.getContent(), orchextraPoint));
        break;
    }

    return new InteractorResponse(triggers);
  }
}
