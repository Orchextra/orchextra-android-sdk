/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.auth;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.vo.Device;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.config.CrmCustomFieldsReader;
import gigigo.com.orchextra.data.datasources.db.config.DeviceCustomFieldsReader;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

//fixme refactor, avoid obtain/retrieve -->get remove/dlete save/store..
public class SessionDBDataSourceImpl implements SessionDBDataSource {

  private final Context context;
  private final SessionUpdater sessionUpdater;
  private final SessionReader sessionReader;
  private final RealmDefaultInstance realmDefaultInstance;
  private final CrmCustomFieldsReader crmCustomFieldsReader;
  private final DeviceCustomFieldsReader deviceCustomFieldsReader;
  private final Device device;

  public SessionDBDataSourceImpl(Context context, Device device, SessionUpdater sessionUpdater,
      SessionReader sessionReader, RealmDefaultInstance realmDefaultInstance,
      CrmCustomFieldsReader crmCustomFieldsReader,
      DeviceCustomFieldsReader deviceCustomFieldsReader) {

    this.context = context;
    this.device = device;
    this.sessionUpdater = sessionUpdater;
    this.sessionReader = sessionReader;
    this.realmDefaultInstance = realmDefaultInstance;
    this.crmCustomFieldsReader = crmCustomFieldsReader;
    this.deviceCustomFieldsReader = deviceCustomFieldsReader;

    System.out.println("REALM ************ SessionDBDataSourceImpl constructor ");
        /*
        //fixme REALM
        SharedPreferences prefs = this.context.getSharedPreferences("com.gigigo.orchextra", Context.MODE_PRIVATE);
        String strBIsSessionInitialized = "com.gigigo.orchextra:bIsSessionInitialized";
        boolean bIsSessionInitialized = prefs.getBoolean(strBIsSessionInitialized, false);
        if (!bIsSessionInitialized) {

            System.out.println("REALM ************ SessionDBDataSourceImpl A BORRAR!! ");
            //fixme quizás mejor q dar de alta un registro con defaultvalues, sea mejor borrar lo q haia
            clearAuthenticatedUser();
            clearAuthenticatedSdk();

//            saveSdkAuthCredentials(new SdkAuthCredentials("", ""));
//            saveSdkAuthResponse(new SdkAuthData("", 0, ""));
//            saveClientAuthCredentials(new ClientAuthCredentials("", ""));
//            saveClientAuthResponse(new ClientAuthData("", "", "", 0));
//            saveUser(new CrmUser("", GenderType.ND, null));
           // prefs.edit().putBoolean(strBIsSessionInitialized, true).commit();
        }
        else
        {
            System.out.println("REALM ************ SessionDBDataSourceImpl NO A BORRAR!! ");
        }
*/

  }

  @Override public boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl saveSdkAuthCredentials ");
    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthCredentials(realm, sdkAuthCredentials);
    } catch (RealmException re) {
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
        System.out.println("REALM ************ SessionDBDataSourceImpl saveSdkAuthCredentials OK");
      }
    }

    return true;
  }

  @Override public boolean saveSdkAuthResponse(SdkAuthData sdkAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl saveSdkAuthResponse ");
    try {
      realm.beginTransaction();
      sessionUpdater.updateSdkAuthResponse(realm, sdkAuthData);
    } catch (RealmException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl saveSdkAuthResponse ERROR ");
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
    System.out.println("REALM ************ SessionDBDataSourceImpl saveSdkAuthResponse OK ");
    return true;
  }

  @Override public boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl saveClientAuthCredentials ");
    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthCredentials(realm, clientAuthCredentials);
    } catch (RealmException re) {
      System.out.println(
          "REALM ************ SessionDBDataSourceImpl saveClientAuthCredentials ERROR "
              + re.toString());
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
      System.out.println(
          "REALM ************ SessionDBDataSourceImpl saveClientAuthCredentials OK ");
    }

    return true;
  }

  @Override public boolean saveClientAuthResponse(ClientAuthData clientAuthData) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl saveClientAuthResponse ");
    try {
      realm.beginTransaction();
      sessionUpdater.updateClientAuthResponse(realm, clientAuthData);
    } catch (RealmException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl saveClientAuthResponse ERROR"
          + re.toString());
      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl saveClientAuthResponse OK ");
    }

    return true;
  }

  @Override public boolean saveUser(CrmUser crmUser) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl saveUser ");
    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crmUser);
    } catch (RealmException re) {
      System.out.println(
          "REALM ************ SessionDBDataSourceImpl saveUser ERROR" + re.toString());

      return false;
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl saveUser OK");
    }

    return true;
  }

  @Override public BusinessObject<ClientAuthData> getSessionToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl getSessionToken ");
    try {
      ClientAuthData clientAuthData = sessionReader.readClientAuthData(realm);
      System.out.println("REALM ************ SessionDBDataSourceImpl getSessionToken  "
          + clientAuthData.toString());

      return new BusinessObject(clientAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl saveUser ERROR");
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl saveUser OK");
    }
  }

  @Override public BusinessObject<SdkAuthData> getDeviceToken() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl getDeviceToken ");

    try {
      SdkAuthData sdkAuthData = sessionReader.readSdkAuthData(realm);
      System.out.println("REALM ************ SessionDBDataSourceImpl getDeviceToken sdkAuthData "
          + sdkAuthData.toString());
      return new BusinessObject(sdkAuthData, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl getDeviceToken ERROR");
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl getDeviceToken OK");
    }
  }

  @Override public BusinessObject<Device> retrieveDevice() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      device.setTags(deviceCustomFieldsReader.readDeviceTags(realm));
      device.setBusinessUnits(deviceCustomFieldsReader.readBusinessUnits(realm));

      return new BusinessObject(device, BusinessError.createOKInstance());
    } catch (RealmException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<CrmUser> getCrm() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      CrmUser crmUser = sessionReader.readCrm(realm);

      crmUser.setTags(crmCustomFieldsReader.readCrmTags(realm));
      crmUser.setBusinessUnits(crmCustomFieldsReader.readBusinessUnits(realm));
      crmUser.setCustomFields(crmCustomFieldsReader.readCustomFields(realm));

      return new BusinessObject(crmUser, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException | NullPointerException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public boolean storeCrm(CrmUser crmUser) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      sessionUpdater.updateCrm(realm, crmUser);
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
    System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedUser ");

    try {
      realm.beginTransaction();
      realm.delete(ClientAuthRealm.class);
    } catch (RealmException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedUser ERROR");
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedUser OK");
    }
  }

  @Override public void clearAuthenticatedSdk() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedSdk ");
    try {
      realm.beginTransaction();

      realm.delete(SdkAuthCredentialsRealm.class);
      realm.delete(SdkAuthRealm.class);
    } catch (RealmException re) {
      System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedSdk ERROR");
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
      System.out.println("REALM ************ SessionDBDataSourceImpl clearAuthenticatedSdk OK");
    }
  }
}
