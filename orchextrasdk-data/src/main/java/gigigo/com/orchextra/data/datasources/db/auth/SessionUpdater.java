package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmObject;

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
    SdkAuthCredentialsRealm realmItem = getRealmItem(realm, SdkAuthCredentialsRealm.class);

    SdkAuthCredentialsRealm sdkAuthCredentialsRealm = sdkCredentialsRealmMapper.modelToData(sdkAuthCredentials);
    if (realmItem == null) {
      sdkAuthCredentialsRealm.setId(RealmDefaultInstance.getNextKey(realm, SdkAuthCredentialsRealm.class));
    } else {
      sdkAuthCredentialsRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(sdkAuthCredentialsRealm);
  }

  public void updateSdkAuthResponse(Realm realm, SdkAuthData sdkAuthData) {
    SdkAuthRealm realmItem = getRealmItem(realm, SdkAuthRealm.class);

    SdkAuthRealm sdkAuthRealm = sdkAuthRealmMapper.modelToData(sdkAuthData);
    if (realmItem == null) {
      sdkAuthRealm.setId(RealmDefaultInstance.getNextKey(realm, SdkAuthRealm.class));
    } else {
      sdkAuthRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(sdkAuthRealm);
  }

  public void updateClientAuthCredentials(Realm realm, ClientAuthCredentials clientAuthCred) {
    ClientAuthCredentialsRealm realmItem = getRealmItem(realm, ClientAuthCredentialsRealm.class);

    ClientAuthCredentialsRealm clientAuthCredentialsRealm = clientCredentialsRealmMapper.modelToData(clientAuthCred);
    if (realmItem == null) {
      clientAuthCredentialsRealm.setId(RealmDefaultInstance.getNextKey(realm, ClientAuthCredentialsRealm.class));
    } else {
      clientAuthCredentialsRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(clientAuthCredentialsRealm);
  }

  public void updateClientAuthResponse(Realm realm, ClientAuthData clientAuthData) {
    ClientAuthRealm realmItem = getRealmItem(realm, ClientAuthRealm.class);

    ClientAuthRealm clientAuthRealm = clientAuthRealmMapper.modelToData(clientAuthData);
    if (realmItem == null) {
      clientAuthRealm.setId(RealmDefaultInstance.getNextKey(realm, ClientAuthRealm.class));
    } else {
      clientAuthRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(clientAuthRealm);
  }

  public void updateCrm(Realm realm, Crm crm) {
    CrmRealm realmItem = getRealmItem(realm, CrmRealm.class);

    CrmRealm crmRealm = crmRealmMapper.modelToData(crm);
    if (realmItem == null) {
      crmRealm.setId(RealmDefaultInstance.getNextKey(realm, CrmRealm.class));
    } else {
      crmRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(crmRealm);
  }

  private <T extends RealmObject> T getRealmItem(Realm realm, Class<T> classType) {
    return realm.where(classType).findFirst();
  }
}
