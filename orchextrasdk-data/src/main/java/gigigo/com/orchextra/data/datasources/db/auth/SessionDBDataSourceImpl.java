package gigigo.com.orchextra.data.datasources.db.auth;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionDBDataSourceImpl implements SessionDBDataSource {

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
    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthCredentials(realm, sdkAuthCredentials);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthResponse(realm, sdkAuthData);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthCredentials(realm, clientAuthCredentials);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthResponse(realm, clientAuthData);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  @Override public boolean saveUser(Crm crm) {
    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crm);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  @Override public BusinessObject<ClientAuthData> getSessionToken() {
    Realm realm = Realm.getDefaultInstance();
    ClientAuthData clientAuthData;

    try {
      realm.beginTransaction();
      clientAuthData = sessionReader.readClientAuthData(realm);
      realm.commitTransaction();
    }catch (NotFountRealmObjectException | RealmException re ){
      return new BusinessObject(null, new BusinessError(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, re.getMessage()));
    }finally {
      realm.close();
    }

    return new BusinessObject(clientAuthData, BusinessError.createOKInstance());
  }

  @Override public BusinessObject<SdkAuthData> getDeviceToken() {
    Realm realm = Realm.getDefaultInstance();
    SdkAuthData sdkAuthData;

    try {
      realm.beginTransaction();
      sdkAuthData = sessionReader.readSdkAuthData(realm);
      realm.commitTransaction();
    }catch (NotFountRealmObjectException | RealmException re ){
      return new BusinessObject(null, new BusinessError(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, re.getMessage()));
    }finally {
      realm.close();
    }

    return new BusinessObject(sdkAuthData, BusinessError.createOKInstance());
  }
}
