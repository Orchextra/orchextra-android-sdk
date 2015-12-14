package gigigo.com.orchextra.data.datasources.datasources.api.model.resquests;

import com.gigigo.orchextra.domain.entities.Credentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public abstract class OrchextraApiAuthRequest {

  @Expose @SerializedName("grantType")
  private final String grantType;

  @Expose @SerializedName("credentials")
  private final ApiCredentials credentials;

  public OrchextraApiAuthRequest(GrantType grantType, Credentials credentials) {
    this.grantType = grantType.getStringValue();
    this.credentials = obtainApiCredentialsFromCredentials(credentials);
  }

  abstract ApiCredentials obtainApiCredentialsFromCredentials(Credentials credentials);

}
