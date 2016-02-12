package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.InteractorErrorChecker;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class SendConfigInteractor implements Interactor<InteractorResponse<OrchextraUpdates>> {

  private final ConfigDataProvider configDataProvider;
  private final AuthenticationDataProvider authenticationDataProvider;
  private final InteractorErrorChecker interactorErrorChecker;

  private App app;
  private Device device;
  private GeoLocation geolocation;

  public SendConfigInteractor(ConfigDataProvider configDataProvider,
                              AuthenticationDataProvider authenticationDataProvider,
                              InteractorErrorChecker interactorErrorChecker) {

    this.configDataProvider = configDataProvider;
    this.authenticationDataProvider = authenticationDataProvider;
    this.interactorErrorChecker = interactorErrorChecker;
  }

  @Override public InteractorResponse<OrchextraUpdates> call() throws Exception {
    Config config = generateConfig();

    BusinessObject<OrchextraUpdates> boOrchextraUpdates = configDataProvider.sendConfigInfo(config);

    if (boOrchextraUpdates.isSuccess()){
      return new InteractorResponse<>(boOrchextraUpdates.getData());
    }else{
     return new InteractorResponse(interactorErrorChecker.checkErrors(boOrchextraUpdates.getBusinessError()));
    }
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

    //TODO Get notification info from app client
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
