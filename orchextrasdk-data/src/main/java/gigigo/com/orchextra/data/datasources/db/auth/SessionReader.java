package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionReader {

  private final RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper;
  private final RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper;

  public SessionReader(RealmMapper<SdkAuthData, SdkAuthRealm> sdkAuthRealmMapper,
      RealmMapper<ClientAuthData, ClientAuthRealm> clientAuthRealmMapper) {
    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
  }

  public ClientAuthData readClientAuthData(Realm realm) {
    RealmResults<ClientAuthRealm> result = realm.where(ClientAuthRealm.class).findAll();
    ClientAuthRealm clientAuthRealm = result.first();
    return clientAuthRealmMapper.dataToModel(clientAuthRealm);
  }

  public SdkAuthData readSdkAuthData(Realm realm) {
    RealmResults<SdkAuthRealm> result = realm.where(SdkAuthRealm.class).findAll();
    SdkAuthRealm sdkAuthRealm = result.first();
    return sdkAuthRealmMapper.dataToModel(sdkAuthRealm);
  }

}
