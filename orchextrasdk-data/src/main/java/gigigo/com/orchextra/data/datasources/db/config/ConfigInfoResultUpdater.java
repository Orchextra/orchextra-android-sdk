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

package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegionUpdates;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofenceUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import io.realm.Realm;


public class ConfigInfoResultUpdater {

  private final ConfigRegionUpdater beaconUpdater;
  private final ConfigGeofenceUpdater geofenceUpdater;
  private final ConfigVuforiaCredentialsUpdater vuforiaUpdater;


  public ConfigInfoResultUpdater(ConfigRegionUpdater beaconUpdater,
      ConfigGeofenceUpdater geofenceUpdater, ConfigVuforiaCredentialsUpdater vuforiaUpdater) {
    this.beaconUpdater = beaconUpdater;
    this.geofenceUpdater = geofenceUpdater;
    this.vuforiaUpdater = vuforiaUpdater;

  }

  public OrchextraUpdates updateConfigInfoV2(Realm realm, ConfigurationInfoResult config) {

    saveRequestWaitTime(realm, config);

    OrchextraRegionUpdates orchextraRegionUpdates =
        beaconUpdater.saveRegions(realm, config.getRegions());

    OrchextraGeofenceUpdates orchextraGeofenceChanges =
        geofenceUpdater.saveGeofences(realm, config.getGeofences());

    VuforiaCredentials vuforiaCredentialsChanges = vuforiaUpdater.saveVuforia(realm, config.getVuforia());



    return new OrchextraUpdates(orchextraRegionUpdates, orchextraGeofenceChanges, vuforiaCredentialsChanges);
  }

  public void saveRequestWaitTime(Realm realm, ConfigurationInfoResult config) {
    ConfigInfoResultRealm configInfoResultRealm = new ConfigInfoResultRealm();
    configInfoResultRealm.setRequestWaitTime(config.getRequestWaitTime());
    realm.clear(ConfigInfoResultRealm.class);
    realm.copyToRealm(configInfoResultRealm);
  }

  public void removeConfigInfo(Realm realm) {
    realm.beginTransaction();
    beaconUpdater.removeRegions(realm);
    geofenceUpdater.removeGeofences(realm);
    vuforiaUpdater.removeVuforia(realm);

    realm.commitTransaction();
  }
}
