package com.gigigo.orchextra.domain.services.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.device.GeolocationManager;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;
import com.gigigo.orchextra.domain.services.DomaninService;
import com.gigigo.orchextra.domain.services.proximity.FutureGeolocation;
import java.util.concurrent.Future;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/2/16.
 */
public class ConfigService implements DomaninService {

  private final ConfigDataProvider configDataProvider;
  private final AuthenticationDataProvider authenticationDataProvider;
  private final ServiceErrorChecker serviceErrorChecker;

  private final App app;
  private final Device device;
  private final FutureGeolocation futureGeolocation;
  private final GeolocationManager geolocationManager;

  public ConfigService(ConfigDataProvider configDataProvider,
      AuthenticationDataProvider authenticationDataProvider,
      ServiceErrorChecker serviceErrorChecker, App app, Device device,
      FutureGeolocation futureGeolocation, GeolocationManager geolocationManager) {

    this.configDataProvider = configDataProvider;
    this.authenticationDataProvider = authenticationDataProvider;
    this.serviceErrorChecker = serviceErrorChecker;

    this.futureGeolocation = futureGeolocation;
    this.device = device;
    this.app = app;
    this.geolocationManager = geolocationManager;
  }

  public InteractorResponse<OrchextraUpdates> refreshConfig() {

    GeoLocation geolocation = getGeolocation();

    Config config = generateConfig(geolocation);

    BusinessObject<OrchextraUpdates> boOrchextraUpdates = configDataProvider.sendConfigInfo(config);

    if (boOrchextraUpdates.isSuccess()){
      return new InteractorResponse<>(boOrchextraUpdates.getData());
    }else{
      return new InteractorResponse(serviceErrorChecker.checkErrors(boOrchextraUpdates.getBusinessError()));
    }
  }

  private Config generateConfig(GeoLocation geoLocation) {

    Config config = new Config();
    config.setApp(app);
    config.setDevice(device);
    config.setGeoLocation(geoLocation);

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

  public Future<GeoLocation> getGeolocationAsync() {
    taskAsync(futureGeolocation);
    return futureGeolocation;
  }

  private void taskAsync(FutureGeolocation future) {
      geolocationManager.setRetrieveGeolocationListener(future);
      geolocationManager.retrieveGeolocation();
  }

  public GeoLocation getGeolocation() {
    try {
      return getGeolocationAsync().get();
    } catch (Exception e) {
      //TODO create null geolocation Instance
      return new GeoLocation();
    }
  }

}
