package com.gigigo.orchextra.dataprovision.config.datasource;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface ConfigDBDataSource {
  boolean saveConfigData(ConfigInfoResult config);
  ConfigInfoResult obtainConfigData();
  Geofence obtainGeofenceById(String uuid);
  Beacon obtainBeaconByUuid(String uuid);
}
