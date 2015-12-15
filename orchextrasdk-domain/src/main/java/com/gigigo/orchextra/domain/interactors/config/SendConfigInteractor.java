package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.entities.Config;
import com.gigigo.orchextra.domain.entities.ProximityInfo;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class SendConfigInteractor implements Interactor<InteractorResponse<ProximityInfo>> {

  private final ConfigDataProvider configDataProvider;
  private Config config;

  public SendConfigInteractor(ConfigDataProvider configDataProvider) {
    this.configDataProvider = configDataProvider;
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  @Override public InteractorResponse<ProximityInfo> call() throws Exception {
    BusinessObject<ProximityInfo> bo = configDataProvider.sendConfigInfo(config);
    //TODO manage Errors
    return new InteractorResponse<>(bo.getData());
  }

}
