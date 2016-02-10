package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.config.Config;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiConfigRequest;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigModelToExternalClassMapper
    implements ModelToExternalClassMapper<Config, OrchextraApiConfigRequest> {

  private final AppModelToExternalClassMapper appRequestMapper;
  private final CrmModelToExternalClassMapper crmRequestMapper;
  private final DeviceModelToExternalClassMapper deviceRequestMapper;
  private final GeoLocationModelToExternalClassMapper geoLocationRequestMapper;
  private final PushNotificationModelToExternalClassMapper pushNotificationRequestMapper;

  public ConfigModelToExternalClassMapper(PushNotificationModelToExternalClassMapper pushNotificationRequestMapper,
      GeoLocationModelToExternalClassMapper geoLocationRequestMapper,
      DeviceModelToExternalClassMapper deviceRequestMapper,
      CrmModelToExternalClassMapper crmRequestMapper,
      AppModelToExternalClassMapper appRequestMapper) {
    this.pushNotificationRequestMapper = pushNotificationRequestMapper;
    this.geoLocationRequestMapper = geoLocationRequestMapper;
    this.deviceRequestMapper = deviceRequestMapper;
    this.crmRequestMapper = crmRequestMapper;
    this.appRequestMapper = appRequestMapper;
  }

  @Override public OrchextraApiConfigRequest modelToExternalClass(Config config) {

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
