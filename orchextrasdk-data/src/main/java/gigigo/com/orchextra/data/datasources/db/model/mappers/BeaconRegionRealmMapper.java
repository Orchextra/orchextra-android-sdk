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
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRegionRealmMapper implements Mapper<OrchextraRegion, BeaconRegionRealm> {

  @Override public BeaconRegionRealm modelToExternalClass(OrchextraRegion region) {
    BeaconRegionRealm regionRealm = new BeaconRegionRealm();

    regionRealm.setCode(region.getCode());
    regionRealm.setUuid(region.getUuid());
    regionRealm.setMajor(region.getMajor());
    regionRealm.setMinor(region.getMinor());
    regionRealm.setActionRelatedCancelable(region.relatedActionIsCancelable());
    regionRealm.setActionRelated(region.getActionRelatedId());

    if (region.getRegionEvent() != null) {
      regionRealm.setEventType(region.getRegionEvent().getStringValue());
    }

    return regionRealm;
  }

  @Override public OrchextraRegion externalClassToModel(BeaconRegionRealm regionRealm) {

    OrchextraRegion region =
        new OrchextraRegion(regionRealm.getCode(), regionRealm.getUuid(), regionRealm.getMajor(),
            regionRealm.getMinor(), regionRealm.isActive());

    region.setActionRelated(
        new ActionRelated(regionRealm.getActionRelated(), regionRealm.isActionRelatedCancelable()));

    if (regionRealm.getEventType() == null) {
      region.setRegionEvent(RegionEventType.EXIT);
    } else {
      region.setRegionEvent(RegionEventType.getTypeFromString(regionRealm.getEventType()));
    }

    return region;
  }
}
