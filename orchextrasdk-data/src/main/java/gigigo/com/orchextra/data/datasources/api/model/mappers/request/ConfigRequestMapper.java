package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.config.Config;
import gigigo.com.orchextra.data.datasources.api.model.resquests.OrchextraApiConfigRequest;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigRequestMapper implements RequestMapper<Config, OrchextraApiConfigRequest> {

  private final AppRequestMapper appRequestMapper;
  private final CrmRequestMapper crmRequestMapper;
  private final DeviceRequestMapper deviceRequestMapper;
  private final GeoLocationRequestMapper geoLocationRequestMapper;
  private final PushNotificationRequestMapper pushNotificationRequestMapper;

  public ConfigRequestMapper(PushNotificationRequestMapper pushNotificationRequestMapper,
      GeoLocationRequestMapper geoLocationRequestMapper, DeviceRequestMapper deviceRequestMapper,
      CrmRequestMapper crmRequestMapper, AppRequestMapper appRequestMapper) {
    this.pushNotificationRequestMapper = pushNotificationRequestMapper;
    this.geoLocationRequestMapper = geoLocationRequestMapper;
    this.deviceRequestMapper = deviceRequestMapper;
    this.crmRequestMapper = crmRequestMapper;
    this.appRequestMapper = appRequestMapper;
  }

  @Override public OrchextraApiConfigRequest modelToData(Config config) {

    OrchextraApiConfigRequest configRequest = new OrchextraApiConfigRequest();

    configRequest.setApp(MapperUtils.checkNullDataRequest(appRequestMapper, config.getApp()));
    configRequest.setCrm(MapperUtils.checkNullDataRequest(crmRequestMapper, config.getCrm()));

    configRequest.setDevice(MapperUtils.checkNullDataRequest(
        deviceRequestMapper, config.getDevice()));

    configRequest.setGeoLocation(
        MapperUtils.checkNullDataRequest(geoLocationRequestMapper, config.getGeoLocation()));

    configRequest.setNotificationPush(
        MapperUtils.checkNullDataRequest(pushNotificationRequestMapper,
            config.getNotificationPush()));

    return configRequest;
  }

}
