package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.entities.App;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.Device;
import com.gigigo.orchextra.domain.entities.GeoLocation;
import com.gigigo.orchextra.domain.entities.NotificationPush;
import com.gigigo.orchextra.domain.entities.config.Config;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class SendConfigInteractor implements Interactor<InteractorResponse<ConfigInfoResult>> {

  private final ConfigDataProvider configDataProvider;
  private final AuthenticationDataProvider authenticationDataProvider;

  private App app;
  private Device device;
  private GeoLocation geolocation;

  public SendConfigInteractor(ConfigDataProvider configDataProvider, AuthenticationDataProvider authenticationDataProvider) {
    this.configDataProvider = configDataProvider;
    this.authenticationDataProvider = authenticationDataProvider;
  }

  @Override public InteractorResponse<ConfigInfoResult> call() throws Exception {
    Config config = generateConfig();

    BusinessObject<ConfigInfoResult> bo = configDataProvider.sendConfigInfo(config);

    //TODO manage Errors
    return new InteractorResponse<>(bo.getData());
  }

  public void setApp(App app) {
    this.app = app;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public void setGeoLocation(GeoLocation geolocation) {
    this.geolocation = geolocation;
  }

  private Config generateConfig() {
    Config config = new Config();
    config.setApp(app);
    config.setDevice(device);
    config.setGeoLocation(geolocation);

    NotificationPush notificationPush = new NotificationPush();
    notificationPush.setToken("qweqweq");
    notificationPush.setSenderId("dadadasdad");
    config.setNotificationPush(notificationPush);

    BusinessObject<Crm> boCrm = authenticationDataProvider.retrieveCrm();
    if (boCrm.isSuccess()) {
      config.setCrm(boCrm.getData());
    }
    return config;
  }
}
