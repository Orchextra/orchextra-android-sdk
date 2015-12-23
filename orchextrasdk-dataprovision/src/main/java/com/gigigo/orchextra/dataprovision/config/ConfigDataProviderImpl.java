package com.gigigo.orchextra.dataprovision.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.entities.config.Config;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

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

  @Override public BusinessObject<ConfigInfoResult> sendConfigInfo(Config config) {
    BusinessObject<ConfigInfoResult> configResponse = configDBDataSource.obtainConfigData();

    if (!configResponse.isSuccess()){
      configResponse = configDataSource.sendConfigInfo(config);
    }

    if (configResponse.isSuccess()){
      configDBDataSource.saveConfigData(configResponse.getData());
    }

    return configResponse;
  }
}
