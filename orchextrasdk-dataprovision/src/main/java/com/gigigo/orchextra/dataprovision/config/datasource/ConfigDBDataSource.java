package com.gigigo.orchextra.dataprovision.config.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface ConfigDBDataSource {
  boolean saveConfigData(ConfigInfoResult config);
  BusinessObject<ConfigInfoResult> obtainConfigData();
  Geofence obtainGeofenceById(String uuid);
  Beacon obtainBeaconByUuid(String uuid);
  BusinessObject<List<Geofence>> obtainGeofences();
}
