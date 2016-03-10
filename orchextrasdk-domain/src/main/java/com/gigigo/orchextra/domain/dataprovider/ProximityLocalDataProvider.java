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

package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

public interface ProximityLocalDataProvider {
  BusinessObject<OrchextraRegion> obtainRegion(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraRegion> storeRegion(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraRegion> deleteRegion(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon);

  BusinessObject<List<OrchextraRegion>> getBeaconRegionsForScan();

  void purgeOldBeaconEventsWithRequestTime(int requestTime);

  BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence geofence);

  BusinessObject<OrchextraGeofence> deleteGeofenceEvent(String geofenceId);

  BusinessObject<OrchextraGeofence> obtainSavedGeofenceInDatabase(String geofenceId);

  BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence geofence);

  BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence);

  BusinessObject<List<OrchextraBeacon>> getNotStoredBeaconEvents(
      List<OrchextraBeacon> orchextraBeacons);
}
