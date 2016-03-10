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

package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviourImpl;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

public class BeaconTrigger extends Trigger {

  private final BeaconDistanceType beaconDistanceType;

  public BeaconTrigger(String code, OrchextraPoint point, AppRunningModeType appRunningModeType,
      BeaconDistanceType beaconDistanceType, GeoPointEventType geoPointEventType) {
    super(TriggerType.BEACON, code, point, appRunningModeType);

    this.beaconDistanceType = beaconDistanceType;
    this.isTriggerable = true;
  }

  public BeaconTrigger(OrchextraBeacon orchextraBeacon, AppRunningModeType appRunningMode) {
    super(TriggerType.BEACON, orchextraBeacon.getCode(), null, appRunningMode);
    this.beaconDistanceType = orchextraBeacon.getBeaconDistance();
  }

  @Override void setConcreteBehaviour() {
    this.beaconDistanceTypeBehaviour = new BeaconDistanceTypeBehaviourImpl(beaconDistanceType);
    this.geoPointEventTypeBehaviour = new GeoPointEventTypeBehaviourImpl(null);
    this.geoDistanceBehaviour = new GeoDistanceBehaviourImpl(0.0);
  }
}
