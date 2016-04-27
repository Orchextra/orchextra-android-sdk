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

import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeaconUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Theme;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import io.realm.Realm;


public class ConfigInfoResultUpdater {

  private final ConfigBeaconUpdater beaconUpdater;
  private final ConfigGeofenceUpdater geofenceUpdater;
  private final ConfigVuforiaUpdater vuforiaUpdater;
  private final ConfigThemeUpdater themeUpdater;

  public ConfigInfoResultUpdater(ConfigBeaconUpdater beaconUpdater,
      ConfigGeofenceUpdater geofenceUpdater, ConfigVuforiaUpdater vuforiaUpdater,
      ConfigThemeUpdater themeUpdater) {
    this.beaconUpdater = beaconUpdater;
    this.geofenceUpdater = geofenceUpdater;
    this.vuforiaUpdater = vuforiaUpdater;
    this.themeUpdater = themeUpdater;
  }

  public OrchextraUpdates updateConfigInfoV2(Realm realm, ConfigInfoResult config) {

    saveRequestWaitTime(realm, config);

    OrchextraBeaconUpdates orchextraBeaconUpdates =
        beaconUpdater.saveRegions(realm, config.getRegions());

    OrchextraGeofenceUpdates orchextraGeofenceChanges =
        geofenceUpdater.saveGeofences(realm, config.getGeofences());

    Vuforia vuforiaChanges = vuforiaUpdater.saveVuforia(realm, config.getVuforia());

    Theme themeChanges = themeUpdater.saveTheme(realm, config.getTheme());

    return new OrchextraUpdates(orchextraBeaconUpdates, orchextraGeofenceChanges, vuforiaChanges,
        themeChanges);
  }

  public void saveRequestWaitTime(Realm realm, ConfigInfoResult config) {
    ConfigInfoResultRealm configInfoResultRealm = new ConfigInfoResultRealm();
    configInfoResultRealm.setRequestWaitTime(config.getRequestWaitTime());
    realm.delete(ConfigInfoResultRealm.class);
    realm.copyToRealm(configInfoResultRealm);
  }
}
