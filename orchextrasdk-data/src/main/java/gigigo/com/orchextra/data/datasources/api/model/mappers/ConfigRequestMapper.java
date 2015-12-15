package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.domain.entities.Config;
import gigigo.com.orchextra.data.datasources.api.model.resquests.OrchextraApiConfigRequest;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigRequestMapper implements Mapper<Config, OrchextraApiConfigRequest> {



  @Override public OrchextraApiConfigRequest modelToData(Config config) {
    OrchextraApiConfigRequest orchextraApiConfigRequest = new OrchextraApiConfigRequest();
    //TODO implement mapper
    return orchextraApiConfigRequest;
  }

  @Override public Config dataToModel(OrchextraApiConfigRequest orchextraApiConfigRequest) {
    Config config = new Config();
    //TODO implement mapper
    return config;
  }

}
