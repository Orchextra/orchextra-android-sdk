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

package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;


public class BeaconEventRealmMapper implements Mapper<OrchextraBeacon, BeaconEventRealm> {

  @Override public OrchextraBeacon externalClassToModel(BeaconEventRealm data) {
    OrchextraBeacon orchextraBeacon =
        new OrchextraBeacon(data.getUuid(), data.getMayor(), data.getMinor(),
            BeaconDistanceType.getValueFromString(data.getBeaconDistance()));

    orchextraBeacon.setCode(data.getCode());

    return orchextraBeacon;
  }

  @Override public BeaconEventRealm modelToExternalClass(OrchextraBeacon orchextraBeacon) {
    BeaconEventRealm beaconEventRealm = new BeaconEventRealm();

    beaconEventRealm.setCode(orchextraBeacon.getCode());
    beaconEventRealm.setUuid(orchextraBeacon.getUuid());
    beaconEventRealm.setMayor(orchextraBeacon.getMayor());
    beaconEventRealm.setMinor(orchextraBeacon.getMinor());
    beaconEventRealm.setKeyForRealm(
        orchextraBeacon.getCode() + orchextraBeacon.getBeaconDistance().getStringValue());
    beaconEventRealm.setBeaconDistance(orchextraBeacon.getBeaconDistance().getStringValue());

    return beaconEventRealm;
  }
}
