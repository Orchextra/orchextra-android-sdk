package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaResponseMapper implements ResponseMapper<Vuforia, ApiVuforia>{

  @Override public Vuforia dataToModel(ApiVuforia apiVuforia) {
    Vuforia vuforia = new Vuforia();

    vuforia.setClientAccessKey(apiVuforia.getClientAccessKey());
    vuforia.setClientSecretKey(apiVuforia.getClientSecretKey());
    vuforia.setLicenseKey(apiVuforia.getLicenseKey());
    vuforia.setServerAccessKey(apiVuforia.getServerAccessKey());
    vuforia.setServerSecretKey(apiVuforia.getServerSecretKey());

    return vuforia;
  }
}
