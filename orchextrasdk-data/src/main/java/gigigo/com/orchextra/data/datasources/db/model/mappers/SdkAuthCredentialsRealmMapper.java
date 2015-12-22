package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SdkAuthCredentialsRealmMapper
    implements RealmMapper<SdkAuthCredentials, SdkAuthCredentialsRealm> {

  @Override public SdkAuthCredentialsRealm modelToData(SdkAuthCredentials credentials) {
    SdkAuthCredentialsRealm sdkAuthCredentialsRealm = new SdkAuthCredentialsRealm();
    sdkAuthCredentialsRealm.setApiKey(sdkAuthCredentialsRealm.getApiSecret());
    sdkAuthCredentialsRealm.setApiSecret(sdkAuthCredentialsRealm.getApiSecret());
    return sdkAuthCredentialsRealm;
  }

  @Override public SdkAuthCredentials dataToModel(SdkAuthCredentialsRealm sdkAuthCredentialsRealm) {

    String apiKey = sdkAuthCredentialsRealm.getApiKey();
    String apiSecret = sdkAuthCredentialsRealm.getApiSecret();

    return new SdkAuthCredentials(apiKey, apiSecret);
  }
}
