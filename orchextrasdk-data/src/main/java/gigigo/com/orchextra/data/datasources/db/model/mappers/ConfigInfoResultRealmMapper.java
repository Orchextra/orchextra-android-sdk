package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultRealmMapper implements RealmMapper<ConfigInfoResult, ConfigInfoResultRealm> {

  @Override public ConfigInfoResultRealm modelToData(ConfigInfoResult configInfoResult) {

    if (configInfoResult.supportsBeacons()){

    }
    return null;
  }

  @Override public ConfigInfoResult dataToModel(ConfigInfoResultRealm configInfoResultRealm) {
    return null;
  }
}
