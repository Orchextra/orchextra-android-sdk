package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.gigigo.orchextra.domain.entities.Credentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class OrchextraApiClientAuthRequest extends OrchextraApiAuthRequest{

  private final String crmId;

  public OrchextraApiClientAuthRequest(GrantType grantType, Credentials credentials, String crmId) {
    super(grantType, credentials);
    this.crmId = crmId;
  }

  @Override ApiCredentials obtainApiCredentialsFromCredentials(Credentials credentials) {
    return new ApiClientAuthCredentials(credentials, crmId);
  }

}
