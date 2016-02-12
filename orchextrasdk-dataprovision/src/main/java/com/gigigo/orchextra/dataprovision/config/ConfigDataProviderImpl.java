package com.gigigo.orchextra.dataprovision.config;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class ConfigDataProviderImpl implements ConfigDataProvider {

  private final ConfigDataSource configDataSource;
  private final ConfigDBDataSource configDBDataSource;
  private final SessionDBDataSource sessionDBDataSource;
  private final Session session;

  public ConfigDataProviderImpl(ConfigDataSource configDataSource,
                                ConfigDBDataSource configDBDataSource,
                                SessionDBDataSource sessionDBDataSource,
                                Session session) {
    this.configDataSource = configDataSource;
    this.configDBDataSource = configDBDataSource;
    this.sessionDBDataSource = sessionDBDataSource;
    this.session = session;
  }

  @Override public BusinessObject<OrchextraUpdates> sendConfigInfo(Config config) {
    BusinessObject<SdkAuthData> deviceToken = sessionDBDataSource.getDeviceToken();
    if (deviceToken.isSuccess()) {
      setAuthenticationToken();
    } else {
      return new BusinessObject<>(null, new BusinessError(401, deviceToken.getBusinessError().getMessage(),
              BusinessContentType.BUSINESS_ERROR_CONTENT));
    }

    BusinessObject<ConfigInfoResult> configResponse = configDataSource.sendConfigInfo(config);

    if (configResponse.isSuccess()){
      OrchextraUpdates orchextraUpdates = configDBDataSource.saveConfigData(configResponse.getData());
      return new BusinessObject(orchextraUpdates, BusinessError.createOKInstance());
    } else {
     return new BusinessObject<>(null,configResponse.getBusinessError());
    }
  }

  private void setAuthenticationToken() {
    BusinessObject<ClientAuthData> credentials = sessionDBDataSource.getSessionToken();

    if (credentials.isSuccess()) {
      session.setTokenString(credentials.getData().getValue());
    }
  }
}
