package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeaconUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Theme;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import io.realm.Realm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
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

    ConfigInfoResultRealm configInfoResultRealm = new ConfigInfoResultRealm();
    configInfoResultRealm.setRequestWaitTime(config.getRequestWaitTime());
    realm.copyToRealm(configInfoResultRealm);

    OrchextraBeaconUpdates orchextraBeaconUpdates =
        beaconUpdater.saveRegions(realm, config.getRegions());

    OrchextraGeofenceUpdates orchextraGeofenceChanges =
        geofenceUpdater.saveGeofences(realm, config.getGeofences());

    Vuforia vuforiaChanges = vuforiaUpdater.saveVuforia(realm, config.getVuforia());

    Theme themeChanges = themeUpdater.saveTheme(realm, config.getTheme());

    return new OrchextraUpdates(orchextraBeaconUpdates, orchextraGeofenceChanges, vuforiaChanges,
        themeChanges);
  }
}
