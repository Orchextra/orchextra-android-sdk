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

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class BeaconExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiBeaconRegion, OrchextraRegion> {

  @Override public OrchextraRegion externalClassToModel(ApiBeaconRegion apiBeaconRegion) {

    OrchextraRegion region =
        new OrchextraRegion(apiBeaconRegion.getCode(), apiBeaconRegion.getUuid(),
            (apiBeaconRegion.getMajor() != null) ? apiBeaconRegion.getMajor() : -1,
            (apiBeaconRegion.getMinor() != null) ? apiBeaconRegion.getMinor() : -1,
            apiBeaconRegion.getActive());

    //Set supper fields
    region.setNotifyOnEntry(apiBeaconRegion.getNotifyOnEntry());
    region.setNotifyOnExit(apiBeaconRegion.getNotifyOnExit());
    region.setTags(apiBeaconRegion.getTags());

    return region;
  }
}
