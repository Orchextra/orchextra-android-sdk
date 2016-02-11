package com.gigigo.orchextra.dataprovision.config;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class ConfigDataProviderImpl implements ConfigDataProvider {

  private final ConfigDataSource configDataSource;
  private final ConfigDBDataSource configDBDataSource;

  public ConfigDataProviderImpl(ConfigDataSource configDataSource,
      ConfigDBDataSource configDBDataSource) {
    this.configDataSource = configDataSource;
    this.configDBDataSource = configDBDataSource;
  }

  @Override public BusinessObject<OrchextraUpdates> sendConfigInfo(Config config) {
    BusinessObject<ConfigInfoResult> configResponse = configDataSource.sendConfigInfo(config);

    OrchextraUpdates orchextraUpdates = null;
    if (configResponse.isSuccess()){
      orchextraUpdates = configDBDataSource.saveConfigData(configResponse.getData());
      return new BusinessObject(orchextraUpdates, BusinessError.createOKInstance());
    } else {
     return new BusinessObject<>(null, BusinessError.createKoInstance(configResponse.getBusinessError().getMessage()));
    }
  }
}
