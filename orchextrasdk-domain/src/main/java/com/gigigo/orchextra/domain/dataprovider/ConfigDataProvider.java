package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Config;
import com.gigigo.orchextra.domain.entities.ConfigInfoResult;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ConfigDataProvider {

  BusinessObject<ConfigInfoResult> sendConfigInfo(Config config);

}
