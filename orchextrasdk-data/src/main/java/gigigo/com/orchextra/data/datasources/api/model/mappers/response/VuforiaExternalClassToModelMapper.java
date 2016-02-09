package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiVuforia, Vuforia> {

  @Override public Vuforia externalClassToModel(ApiVuforia apiVuforia) {
    Vuforia vuforia = new Vuforia();

    vuforia.setClientAccessKey(apiVuforia.getClientAccessKey());
    vuforia.setClientSecretKey(apiVuforia.getClientSecretKey());
    vuforia.setLicenseKey(apiVuforia.getLicenseKey());
    vuforia.setServerAccessKey(apiVuforia.getServerAccessKey());
    vuforia.setServerSecretKey(apiVuforia.getServerSecretKey());

    return vuforia;
  }
}
