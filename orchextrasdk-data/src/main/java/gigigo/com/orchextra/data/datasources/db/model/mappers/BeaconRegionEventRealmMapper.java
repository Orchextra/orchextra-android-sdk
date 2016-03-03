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
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelated;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.RegionEventType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRegionEventRealmMapper
    implements Mapper<OrchextraRegion, BeaconRegionEventRealm> {

  private final Mapper<OrchextraRegion, BeaconRegionRealm> beaconRegionRealmMapper;

  public BeaconRegionEventRealmMapper(
      Mapper<OrchextraRegion, BeaconRegionRealm> beaconRegionRealmMapper) {
    this.beaconRegionRealmMapper = beaconRegionRealmMapper;
  }

  @Override public BeaconRegionEventRealm modelToExternalClass(OrchextraRegion region) {
    BeaconRegionRealm beaconRegionRealm = beaconRegionRealmMapper.modelToExternalClass(region);
    BeaconRegionEventRealm regionEventRealm = new BeaconRegionEventRealm(beaconRegionRealm);
    return regionEventRealm;
  }

  @Override public OrchextraRegion externalClassToModel(BeaconRegionEventRealm regionEventRealm) {
    OrchextraRegion orchextraRegion =
        new OrchextraRegion(regionEventRealm.getCode(), regionEventRealm.getUuid(),
            regionEventRealm.getMajor(), regionEventRealm.getMinor(), regionEventRealm.isActive());

    orchextraRegion.setActionRelated(new ActionRelated(regionEventRealm.getActionRelated(),
        regionEventRealm.isActionRelatedCancelable()));

    orchextraRegion.setRegionEvent(
        RegionEventType.getTypeFromString(regionEventRealm.getEventType()));
    return orchextraRegion;
  }
}
