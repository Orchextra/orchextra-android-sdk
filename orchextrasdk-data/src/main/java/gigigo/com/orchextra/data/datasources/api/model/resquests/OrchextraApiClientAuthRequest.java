package gigigo.com.orchextra.data.datasources.api.model.resquests;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class OrchextraApiClientAuthRequest extends OrchextraApiAuthRequest{

  @Expose @SerializedName("apiKey")
  private final String apiKey;

  @Expose @SerializedName("apiSecret")
  private final String apiSecret;

  public OrchextraApiClientAuthRequest(GrantType grantType, Credentials credentials) {
    super(grantType, credentials);
    SdkAuthCredentials sdkCredentials = ConsistencyUtils.checkInstance(credentials, SdkAuthCredentials.class);
    this.apiKey = sdkCredentials.getApiKey();
    this.apiSecret = sdkCredentials.getApiSecret();
  }

  @Override ApiCredentials obtainApiCredentialsFromCredentials(Credentials credentials) {
    return new ApiClientAuthCredentials(credentials);
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }
}
