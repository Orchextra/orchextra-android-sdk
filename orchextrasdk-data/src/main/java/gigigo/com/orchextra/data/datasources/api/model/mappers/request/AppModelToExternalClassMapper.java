package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.App;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiApp;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class AppModelToExternalClassMapper implements ModelToExternalClassMapper<App, ApiApp> {

  @Override public ApiApp modelToExternalClass(App app) {
    ApiApp apiApp = new ApiApp();

    apiApp.setAppVersion(app.getAppVersion());
    apiApp.setBuildVersion(app.getBuildVersion());
    apiApp.setBundleId(app.getBundleId());

    return apiApp;
  }
}
