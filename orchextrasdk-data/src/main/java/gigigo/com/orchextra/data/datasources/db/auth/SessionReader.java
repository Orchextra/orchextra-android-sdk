package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionReader {

  private final RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper;
  private final RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper;
  private final CrmRealmMapper crmRealmMapper;

    public SessionReader(RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper,
      RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper,
      CrmRealmMapper crmRealmMapper) {

    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
    this.crmRealmMapper = crmRealmMapper;
  }

  public ClientAuthData readClientAuthData(Realm realm) throws NullPointerException{
      ClientAuthRealm clientAuthRealm = realm.where(ClientAuthRealm.class).findFirst();
      return clientAuthRealmMapper.dataToModel(clientAuthRealm);
  }

  public SdkAuthData readSdkAuthData(Realm realm) throws NullPointerException{
      SdkAuthRealm sdkAuthRealm = realm.where(SdkAuthRealm.class).findFirst();
      return sdkAuthRealmMapper.dataToModel(sdkAuthRealm);
  }

    public Crm readCrmId(Realm realm) {
      CrmRealm crmRealm = realm.where(CrmRealm.class).findFirst();
      return crmRealmMapper.dataToModel(crmRealm);
    }
}
