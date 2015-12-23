package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.SessionRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionUpdater {

  private final RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper;
  private final RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper;
  private final RealmMapper<Crm, CrmRealm> crmRealmMapper;
  private final RealmMapper<SdkAuthCredentials, SdkAuthCredentialsRealm> sdkCredentialsRealmMapper;
  private final RealmMapper<ClientAuthCredentials, ClientAuthCredentialsRealm> clientCredentialsRealmMapper;

  public SessionUpdater(RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper,
      RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper,
      RealmMapper<Crm, CrmRealm> crmRealmMapper,
      RealmMapper<SdkAuthCredentials, SdkAuthCredentialsRealm> sdkCredentialsRealmMapper,
      RealmMapper<ClientAuthCredentials, ClientAuthCredentialsRealm> clientCredentialsRealmMapper) {

    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
    this.crmRealmMapper = crmRealmMapper;
    this.sdkCredentialsRealmMapper = sdkCredentialsRealmMapper;
    this.clientCredentialsRealmMapper = clientCredentialsRealmMapper;
  }

  public void updateSdkAuthCredentials(Realm realm, SdkAuthCredentials sdkAuthCredentials) {
    SessionRealm sessionRealm = getSessionRealm(realm);
    sessionRealm.setSdkAuthCredentialsRealm(
        sdkCredentialsRealmMapper.modelToData(sdkAuthCredentials));
    realm.copyToRealmOrUpdate(sessionRealm);
  }

  public void updateSdkAuthResponse(Realm realm, SdkAuthData sdkAuthData) {
    SessionRealm sessionRealm = getSessionRealm(realm);
    sessionRealm.setSdkAuthRealm(sdkAuthRealmMapper.modelToData(sdkAuthData));
    realm.copyToRealmOrUpdate(sessionRealm);
  }

  public void updateClientAuthCredentials(Realm realm, ClientAuthCredentials clientAuthCred) {
    SessionRealm sessionRealm = getSessionRealm(realm);
    sessionRealm.setClientAuthCredentialsRealm(clientCredentialsRealmMapper.modelToData(clientAuthCred));
    realm.copyToRealmOrUpdate(sessionRealm);
  }

  public void updateClientAuthResponse(Realm realm, ClientAuthData clientAuthData) {
    SessionRealm sessionRealm = getSessionRealm(realm);
    sessionRealm.setClientAuthRealm(clientAuthRealmMapper.modelToData(clientAuthData));
    realm.copyToRealmOrUpdate(sessionRealm);
  }

  public void updateCrm(Realm realm, Crm crm) {
    SessionRealm sessionRealm = getSessionRealm(realm);
    sessionRealm.setCrmRealm(crmRealmMapper.modelToData(crm));
    realm.copyToRealmOrUpdate(sessionRealm);
  }

  private SessionRealm getSessionRealm(Realm realm) {

    RealmResults<SessionRealm> result = realm.where(SessionRealm.class).findAll();

    if (result.isValid() && result.size()>0){
      return result.first();
    }else{
      return new SessionRealm();
    }

  }
}
