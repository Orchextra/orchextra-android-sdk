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

package gigigo.com.orchextra.data.datasources.db.proximity;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.db.model.RegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class RegionEventsReader {

  private final Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper;
  private final OrchextraLogger orchextraLogger;

  public RegionEventsReader(Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper,
                            OrchextraLogger orchextraLogger) {
    this.regionEventRealmMapper = regionEventRealmMapper;
    this.orchextraLogger = orchextraLogger;
  }

  public OrchextraRegion obtainRegionEvent(Realm realm, OrchextraRegion orchextraRegion) {

    RealmResults<RegionEventRealm> results = realm.where(RegionEventRealm.class)
        .equalTo(RegionRealm.CODE_FIELD_NAME, orchextraRegion.getCode())
        .findAll();

    if (results.size() > 1) {
      orchextraLogger.log("EVENT: More than one region Event with same Code stored", OrchextraSDKLogLevel.ERROR);
    } else if (results.size() == 1) {
      orchextraLogger.log("EVENT: Recovered orchextra region " + orchextraRegion.getCode());
    } else {
      orchextraLogger.log("EVENT: Region Event not stored " + orchextraRegion.getCode());
    }

    return regionEventRealmMapper.externalClassToModel(results.first());
  }
}
