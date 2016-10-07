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

package com.gigigo.orchextra.dataprovision.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.ProximityDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.ProximityAndGeofencesLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

public class ProximityAndGeofencesLocalDataProviderImp implements ProximityAndGeofencesLocalDataProvider {

  private final TriggersConfigurationDBDataSource triggersConfigurationDBDataSource;
  private final ProximityDBDataSource proximityDBDataSource;
  private final GeofenceDBDataSource geofenceDBDataSource;

  public ProximityAndGeofencesLocalDataProviderImp(TriggersConfigurationDBDataSource triggersConfigurationDBDataSource,
                                                   ProximityDBDataSource proximityDBDataSource, GeofenceDBDataSource geofenceDBDataSource) {
    this.triggersConfigurationDBDataSource = triggersConfigurationDBDataSource;
    this.proximityDBDataSource = proximityDBDataSource;
    this.geofenceDBDataSource = geofenceDBDataSource;
  }

  @Override
  public BusinessObject<OrchextraGeofence> obtainSavedGeofenceInDatabase(String geofenceId) {
    return triggersConfigurationDBDataSource.obtainGeofenceById(geofenceId);
  }

  @Override
  public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence geofence) {
    return geofenceDBDataSource.obtainGeofenceEvent(geofence);
  }

  @Override
  public BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence) {
    return geofenceDBDataSource.updateGeofenceWithActionId(geofence);
  }

  @Override
  public BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence geofence) {
    return geofenceDBDataSource.storeGeofenceEvent(geofence);
  }

  @Override public BusinessObject<OrchextraGeofence> deleteGeofenceEvent(String geofenceId) {
    BusinessObject<OrchextraGeofence> bo = obtainSavedGeofenceInDatabase(geofenceId);
    if (bo.isSuccess()) {
      return geofenceDBDataSource.deleteGeofenceEvent(bo.getData());
    }
    return new BusinessObject<>(null, bo.getBusinessError());
  }

  @Override public BusinessObject<List<OrchextraRegion>> getBeaconRegionsForScan() {
    return triggersConfigurationDBDataSource.obtainRegionsForScan();
  }

  @Override public BusinessObject<OrchextraRegion> obtainRegion(OrchextraRegion orchextraRegion) {
    return proximityDBDataSource.obtainRegionEvent(orchextraRegion);
  }

  @Override public BusinessObject<OrchextraRegion> storeRegion(OrchextraRegion orchextraRegion) {
    return proximityDBDataSource.storeRegionEvent(orchextraRegion);
  }

  @Override public BusinessObject<OrchextraRegion> deleteRegion(OrchextraRegion orchextraRegion) {
    return proximityDBDataSource.deleteRegionEvent(orchextraRegion);
  }

  @Override public BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon) {
    return proximityDBDataSource.storeBeaconEvent(beacon);
  }

  @Override public void purgeOldBeaconEventsWithRequestTime(int requestTime) {
    proximityDBDataSource.deleteAllBeaconsInListWithTimeStampt(requestTime);
  }

  @Override public BusinessObject<List<OrchextraBeacon>> getNotStoredBeaconEvents(
      List<OrchextraBeacon> beacons) {
    return proximityDBDataSource.getNotStoredBeaconEvents(beacons);
  }

  @Override public BusinessObject<List<OrchextraGeofence>> obtainGeofencesForRegister() {
    return triggersConfigurationDBDataSource.obtainGeofencesForRegister();
  }

  @Override
  public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
    return proximityDBDataSource.updateRegionWithActionId(orchextraRegion);
  }
}
