package gigigo.com.orchextra.data.datasources.datasources.api.model.resquests;

import com.gigigo.ggglib.general.utils.Utils;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiSdkAuthCredentials implements ApiCredentials {


  public ApiSdkAuthCredentials(Credentials credentials) {
    SdkAuthCredentials sdkCredentials = Utils.checkInstance(credentials, SdkAuthCredentials.class);


  }
}
