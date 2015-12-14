package gigigo.com.orchextra.data.datasources.datasources.api.model.resquests;

import com.gigigo.orchextra.domain.entities.Credentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class OrchextraApiSdkClientRequest extends OrchextraApiAuthRequest{

  public OrchextraApiSdkClientRequest(GrantType grantType, Credentials credentials) {
    super(grantType, credentials);
  }

  @Override ApiCredentials obtainApiCredentialsFromCredentials(Credentials credentials) {
    return new ApiSdkAuthCredentials(credentials);
  }
}
