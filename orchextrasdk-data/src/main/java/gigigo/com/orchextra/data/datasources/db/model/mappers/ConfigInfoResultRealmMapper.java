package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultRealmMapper implements
    Mapper<ConfigInfoResult, ConfigInfoResultRealm> {

  @Override public ConfigInfoResultRealm modelToExternalClass(ConfigInfoResult configInfoResult) {

    if (configInfoResult.supportsBeacons()){

    }
    return null;
  }

  @Override public ConfigInfoResult externalClassToModel(
      ConfigInfoResultRealm configInfoResultRealm) {
    return null;
  }
}
