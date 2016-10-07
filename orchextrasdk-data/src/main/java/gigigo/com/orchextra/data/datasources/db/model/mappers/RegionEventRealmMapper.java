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
import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelatedWithRegionAndGeofences;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.RegionEventType;
import gigigo.com.orchextra.data.datasources.db.model.RegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;


public class RegionEventRealmMapper
    implements Mapper<OrchextraRegion, RegionEventRealm> {

  private final Mapper<OrchextraRegion, RegionRealm> beaconRegionRealmMapper;

  public RegionEventRealmMapper(
      Mapper<OrchextraRegion, RegionRealm> beaconRegionRealmMapper) {
    this.beaconRegionRealmMapper = beaconRegionRealmMapper;
  }

  @Override public RegionEventRealm modelToExternalClass(OrchextraRegion region) {
    RegionRealm regionRealm = beaconRegionRealmMapper.modelToExternalClass(region);
    RegionEventRealm regionEventRealm = new RegionEventRealm(regionRealm);
    return regionEventRealm;
  }

  @Override public OrchextraRegion externalClassToModel(RegionEventRealm regionEventRealm) {
    OrchextraRegion orchextraRegion =
        new OrchextraRegion(regionEventRealm.getCode(), regionEventRealm.getUuid(),
            regionEventRealm.getMajor(), regionEventRealm.getMinor(), regionEventRealm.isActive());

    orchextraRegion.setActionRelatedWithRegionAndGeofences(new ActionRelatedWithRegionAndGeofences(regionEventRealm.getActionRelated(),
        regionEventRealm.isActionRelatedCancelable()));

    orchextraRegion.setRegionEvent(
        RegionEventType.getTypeFromString(regionEventRealm.getEventType()));
    return orchextraRegion;
  }
}
