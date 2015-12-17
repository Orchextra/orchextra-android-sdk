package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.Config;
import gigigo.com.orchextra.data.datasources.api.model.resquests.OrchextraApiConfigRequest;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigRequestMapper implements RequestMapper<Config, OrchextraApiConfigRequest> {

  AppRequestMapper appRequestMapper;

  @Override public OrchextraApiConfigRequest modelToData(Config config) {
    OrchextraApiConfigRequest configRequest = new OrchextraApiConfigRequest();
    configRequest.setApp(MapperUtils.checkNullDataRequest(appRequestMapper, config.getApp()));
    return configRequest;
  }

}
