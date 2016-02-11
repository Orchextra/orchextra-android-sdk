package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ConfigDataProvider {

  BusinessObject sendConfigInfo(Config config);

}
