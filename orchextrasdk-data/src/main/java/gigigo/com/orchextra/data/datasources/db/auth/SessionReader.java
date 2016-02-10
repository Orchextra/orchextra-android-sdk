package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import io.realm.Realm;

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
      ClientAuthRealm clientAuthRealm = realm.where(ClientAuthRealm.class).findFirst();
      return clientAuthRealmMapper.externalClassToModel(clientAuthRealm);
  }

  public SdkAuthData readSdkAuthData(Realm realm) throws NullPointerException{
      SdkAuthRealm sdkAuthRealm = realm.where(SdkAuthRealm.class).findFirst();
      return sdkAuthRealmMapper.externalClassToModel(sdkAuthRealm);
  }

    public Crm readCrmId(Realm realm) {
      CrmRealm crmRealm = realm.where(CrmRealm.class).findFirst();
      return crmRealmMapper.externalClassToModel(crmRealm);
    }
}
