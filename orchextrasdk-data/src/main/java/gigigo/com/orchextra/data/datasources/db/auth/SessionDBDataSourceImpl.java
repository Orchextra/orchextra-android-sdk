package gigigo.com.orchextra.data.datasources.db.auth;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionDBDataSourceImpl extends RealmDefaultInstance implements SessionDBDataSource {

  private final Context context;
  private final SessionUpdater sessionUpdater;
  private final SessionReader sessionReader;

  public SessionDBDataSourceImpl(Context context, SessionUpdater sessionUpdater,
      SessionReader sessionReader) {

    this.context = context;
    this.sessionUpdater = sessionUpdater;
    this.sessionReader = sessionReader;
  }

  @Override public boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthCredentials(realm, sdkAuthCredentials);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      realm.close();
    }

    return true;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthResponse(realm, sdkAuthData);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      if (realm != null) {
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthCredentials(realm, clientAuthCredentials);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      realm.close();
    }

    return true;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthResponse(realm, clientAuthData);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      realm.close();
    }

    return true;
  }

  @Override public boolean saveUser(Crm crm) {
    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crm);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      realm.close();
    }

    return true;
  }

  @Override public BusinessObject<ClientAuthData> getSessionToken() {
    Realm realm = getRealmInstance(context);

    try {
      ClientAuthData clientAuthData = sessionReader.readClientAuthData(realm);
      return new BusinessObject(clientAuthData, BusinessError.createOKInstance());
    }catch (NotFountRealmObjectException | RealmException | NullPointerException re ){
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    }finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<SdkAuthData> getDeviceToken() {
    Realm realm = getRealmInstance(context);

    try {
      SdkAuthData sdkAuthData = sessionReader.readSdkAuthData(realm);
      return new BusinessObject(sdkAuthData, BusinessError.createOKInstance());
    }catch (NotFountRealmObjectException | RealmException | NullPointerException re ){
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    }finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<Crm> getCrm() {
    Realm realm = getRealmInstance(context);

    try {
      Crm crm = sessionReader.readCrmId(realm);
      return new BusinessObject(crm, BusinessError.createOKInstance());
    }catch (NotFountRealmObjectException | RealmException | NullPointerException re ){
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    }finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public boolean saveUserId(String crmId) {

    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrmID(realm, crmId);
    }catch (RealmException re){
      return false;
    }finally {
      realm.commitTransaction();
      realm.close();
    }
    return true;
  }

}
