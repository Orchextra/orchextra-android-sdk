package com.gigigo.orchextra.dataprovision.config.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.config.Config;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ConfigDataSource {

  BusinessObject<ConfigInfoResult> sendConfigInfo(Config config);

}
