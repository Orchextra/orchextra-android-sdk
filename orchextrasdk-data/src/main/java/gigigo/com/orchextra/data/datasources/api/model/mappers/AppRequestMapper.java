package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.App;
import gigigo.com.orchextra.data.datasources.api.model.resquests.ApiApp;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class AppRequestMapper implements RequestMapper<App, ApiApp> {

  @Override public ApiApp modelToData(App app) {
    ApiApp apiApp = new ApiApp();

    return apiApp;
  }
}
