package gigigo.com.orchextra.data.datasources.api.model.resquests;

import com.gigigo.ggglib.general.utils.Utils;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiSdkAuthCredentials implements ApiCredentials {

  @Expose @SerializedName("apiKey")
  private final String apiKey;

  @Expose @SerializedName("apiSecret")
  private final String apiSecret;


  public ApiSdkAuthCredentials(Credentials credentials) {
    SdkAuthCredentials sdkCredentials = Utils.checkInstance(credentials, SdkAuthCredentials.class);
    this.apiKey = sdkCredentials.getApiKey();
    this.apiSecret = sdkCredentials.getApiSecret();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }
}
