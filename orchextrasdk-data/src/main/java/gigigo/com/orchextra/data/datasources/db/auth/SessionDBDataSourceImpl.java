package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.orchextra.dataprovision.config.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import com.gigigo.orchextra.domain.entities.SessionToken;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionDBDataSourceImpl implements SessionDBDataSource {

  @Override public boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    return false;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    return false;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    return false;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    return false;
  }

  @Override public boolean saveUser(Crm crm) {
    return false;
  }

  @Override public SessionToken getSessionToken() {
    return null;
  }
}
