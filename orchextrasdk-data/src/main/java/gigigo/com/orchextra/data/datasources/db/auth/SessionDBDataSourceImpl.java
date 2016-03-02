package gigigo.com.orchextra.data.datasources.db.auth;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
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
  private final RealmDefaultInstance realmDefaultInstance;

  public SessionDBDataSourceImpl(Context context, SessionUpdater sessionUpdater,
      SessionReader sessionReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.sessionUpdater = sessionUpdater;
    this.sessionReader = sessionReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  @Override public boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthCredentials(realm, sdkAuthCredentials);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthResponse(realm, sdkAuthData);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthCredentials(realm, clientAuthCredentials);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthResponse(realm, clientAuthData);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public boolean saveUser(Crm crm) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crm);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }

    return true;
  }

  @Override public BusinessObject<ClientAuthData> getSessionToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ClientAuthData clientAuthData = sessionReader.readClientAuthData(realm);
      return new BusinessObject(clientAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<SdkAuthData> getDeviceToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      SdkAuthData sdkAuthData = sessionReader.readSdkAuthData(realm);
      return new BusinessObject(sdkAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<Crm> getCrm() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      Crm crm = sessionReader.readCrm(realm);
      return new BusinessObject(crm, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public boolean storeCrm(Crm crm) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crm);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
    return true;
  }

  @Override public void clearAuthenticatedUser() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      realm.beginTransaction();
      realm.clear(ClientAuthRealm.class);
    } catch (RealmException re) {

    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
  }
}
