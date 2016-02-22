package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionReader {

  private final ExternalClassToModelMapper<SdkAuthRealm, SdkAuthData> sdkAuthRealmMapper;
  private final ExternalClassToModelMapper<ClientAuthRealm, ClientAuthData> clientAuthRealmMapper;
  private final CrmRealmMapper crmRealmMapper;

    public SessionReader(ExternalClassToModelMapper sdkAuthRealmMapper,
        ExternalClassToModelMapper clientAuthRealmMapper,
      CrmRealmMapper crmRealmMapper) {

    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
    this.crmRealmMapper = crmRealmMapper;
  }

  public ClientAuthData readClientAuthData(Realm realm) throws NullPointerException{
      RealmResults<ClientAuthRealm> clientAuthRealm = realm.where(ClientAuthRealm.class).findAll();
      if (clientAuthRealm.size() > 0) {
          return clientAuthRealmMapper.externalClassToModel(clientAuthRealm.first());
      } else {
          throw new NotFountRealmObjectException();
      }
  }

  public SdkAuthData readSdkAuthData(Realm realm) throws NullPointerException{
      RealmResults<SdkAuthRealm> sdkAuthRealm = realm.where(SdkAuthRealm.class).findAll();
      if (sdkAuthRealm.size() > 0) {
          return sdkAuthRealmMapper.externalClassToModel(sdkAuthRealm.first());
      } else {
          throw new NotFountRealmObjectException();
      }
  }

    public Crm readCrmId(Realm realm) {
      RealmResults<CrmRealm> crmRealm = realm.where(CrmRealm.class).findAll();
      if (crmRealm.size() > 0) {
          return crmRealmMapper.externalClassToModel(crmRealm.first());
      } else {
        throw new NotFountRealmObjectException();
      }
    }
}
