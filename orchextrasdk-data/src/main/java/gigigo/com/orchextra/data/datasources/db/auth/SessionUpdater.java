package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionUpdater {

  private final ModelToExternalClassMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper;
  private final ModelToExternalClassMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper;
  private final ModelToExternalClassMapper<Crm, CrmRealm> crmRealmMapper;
  private final ModelToExternalClassMapper<SdkAuthCredentials, SdkAuthCredentialsRealm>
      sdkCredentialsRealmMapper;
  private final ModelToExternalClassMapper<ClientAuthCredentials, ClientAuthCredentialsRealm>
      clientCredentialsRealmMapper;

  public SessionUpdater(ModelToExternalClassMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper,
      ModelToExternalClassMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper,
      ModelToExternalClassMapper<Crm, CrmRealm> crmRealmMapper,
      ModelToExternalClassMapper<SdkAuthCredentials, SdkAuthCredentialsRealm> sdkCredentialsRealmMapper,
      ModelToExternalClassMapper<ClientAuthCredentials, ClientAuthCredentialsRealm> clientCredentialsRealmMapper) {

    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
    this.crmRealmMapper = crmRealmMapper;
    this.sdkCredentialsRealmMapper = sdkCredentialsRealmMapper;
    this.clientCredentialsRealmMapper = clientCredentialsRealmMapper;
  }

  public void updateSdkAuthCredentials(Realm realm, SdkAuthCredentials sdkAuthCredentials) {
    SdkAuthCredentialsRealm realmItem = getRealmItem(realm, SdkAuthCredentialsRealm.class);

    SdkAuthCredentialsRealm sdkAuthCredentialsRealm =
        sdkCredentialsRealmMapper.modelToExternalClass(sdkAuthCredentials);
    if (realmItem == null) {
      sdkAuthCredentialsRealm.setId(
          RealmDefaultInstance.getNextKey(realm, SdkAuthCredentialsRealm.class));
    } else {
      sdkAuthCredentialsRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(sdkAuthCredentialsRealm);
  }

  public void updateSdkAuthResponse(Realm realm, SdkAuthData sdkAuthData) {
    SdkAuthRealm realmItem = getRealmItem(realm, SdkAuthRealm.class);

    SdkAuthRealm sdkAuthRealm = sdkAuthRealmMapper.modelToExternalClass(sdkAuthData);
    if (realmItem == null) {
      sdkAuthRealm.setId(RealmDefaultInstance.getNextKey(realm, SdkAuthRealm.class));
    } else {
      sdkAuthRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(sdkAuthRealm);
  }

  public void updateClientAuthCredentials(Realm realm, ClientAuthCredentials clientAuthCred) {
    ClientAuthCredentialsRealm realmItem = getRealmItem(realm, ClientAuthCredentialsRealm.class);

    ClientAuthCredentialsRealm clientAuthCredentialsRealm =
        clientCredentialsRealmMapper.modelToExternalClass(clientAuthCred);
    if (realmItem == null) {
      clientAuthCredentialsRealm.setId(
          RealmDefaultInstance.getNextKey(realm, ClientAuthCredentialsRealm.class));
    } else {
      clientAuthCredentialsRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(clientAuthCredentialsRealm);
  }

  public void updateClientAuthResponse(Realm realm, ClientAuthData clientAuthData) {
    ClientAuthRealm realmItem = getRealmItem(realm, ClientAuthRealm.class);

    ClientAuthRealm clientAuthRealm = clientAuthRealmMapper.modelToExternalClass(clientAuthData);
    if (realmItem == null) {
      clientAuthRealm.setId(RealmDefaultInstance.getNextKey(realm, ClientAuthRealm.class));
    } else {
      clientAuthRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(clientAuthRealm);
  }

  public void updateCrm(Realm realm, Crm crm) {
    CrmRealm realmItem = getRealmItem(realm, CrmRealm.class);

    CrmRealm crmRealm = crmRealmMapper.modelToExternalClass(crm);
    if (realmItem == null) {
      crmRealm.setId(RealmDefaultInstance.getNextKey(realm, CrmRealm.class));
    } else {
      crmRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(crmRealm);
  }

  public void updateCrm(Realm realm, String crmId) {

    CrmRealm realmItem = getRealmItem(realm, CrmRealm.class);

    if (realmItem == null) {
      realmItem = new CrmRealm();
      realmItem.setId(RealmDefaultInstance.getNextKey(realm, CrmRealm.class));
    }

    realmItem.setCrmId(crmId);

    realm.copyToRealmOrUpdate(realmItem);
  }

  private <T extends RealmObject> T getRealmItem(Realm realm, Class<T> classType) {
    return realm.where(classType).findFirst();
  }
}
