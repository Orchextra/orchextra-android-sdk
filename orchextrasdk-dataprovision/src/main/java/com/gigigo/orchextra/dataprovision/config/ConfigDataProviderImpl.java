package com.gigigo.orchextra.dataprovision.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
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

  public ConfigDataProviderImpl(ConfigDataSource configDataSource) {
    this.configDataSource = configDataSource;
  }

  @Override public BusinessObject<ConfigInfoResult> sendConfigInfo(Config config) {
    return configDataSource.sendConfigInfo(config);
  }
}
