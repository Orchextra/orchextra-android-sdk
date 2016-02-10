package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SdkAuthCredentialsRealmMapper implements
    Mapper<SdkAuthCredentials, SdkAuthCredentialsRealm> {

  @Override public SdkAuthCredentialsRealm modelToExternalClass(SdkAuthCredentials credentials) {
    SdkAuthCredentialsRealm sdkAuthCredentialsRealm = new SdkAuthCredentialsRealm();
    sdkAuthCredentialsRealm.setApiKey(credentials.getApiKey());
    sdkAuthCredentialsRealm.setApiSecret(credentials.getApiSecret());
    return sdkAuthCredentialsRealm;
  }

  @Override public SdkAuthCredentials externalClassToModel(
      SdkAuthCredentialsRealm sdkAuthCredentialsRealm) {

    String apiKey = sdkAuthCredentialsRealm.getApiKey();
    String apiSecret = sdkAuthCredentialsRealm.getApiSecret();

    return new SdkAuthCredentials(apiKey, apiSecret);
  }
}
