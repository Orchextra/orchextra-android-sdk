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

package gigigo.com.orchextra.data.datasources.db.status;

import android.content.Context;
import android.content.SharedPreferences;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.OrchextraStatusDBDataSource;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

//fixme refactor, avoid obtain/retrieve -->get remove/dlete save/store..
public class OrchextraStatusDBDataSourceImpl implements OrchextraStatusDBDataSource {

  private final Context context;
  private final OrchextraStatusUpdater orchextraStatusUpdater;
  private final OrchextraStatusReader orchextraStatusReader;
  private final RealmDefaultInstance realmDefaultInstance;
  private final OrchextraLogger orchextraLogger;

  public OrchextraStatusDBDataSourceImpl(Context context,
      OrchextraStatusUpdater orchextraStatusUpdater, OrchextraStatusReader orchextraStatusReader,
      RealmDefaultInstance realmDefaultInstance, OrchextraLogger orchextraLogger) {

    this.context = context;
    this.orchextraStatusUpdater = orchextraStatusUpdater;
    this.orchextraStatusReader = orchextraStatusReader;
    this.realmDefaultInstance = realmDefaultInstance;
    this.orchextraLogger = orchextraLogger;

    System.out.println("REALM ########## OrchextraStatusDBDataSourceImpl constructor");
  }

  @Override public BusinessObject<OrchextraStatus> saveStatus(OrchextraStatus orchextraStatus) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    OrchextraStatus result = null;
    try {
      System.out.println("REALM ************ saveStatus "
          + orchextraStatus.isStarted()
          + orchextraStatus.isInitialized());
      realm.beginTransaction();
      result = orchextraStatusUpdater.saveStatus(realm, orchextraStatus);
      System.out.println("REALM ************ saveStatus, result: \n  "
          + result
          + " guardando:"
          + orchextraStatus.toString());
      //TODO Store crm and session
    } catch (RealmException re) {
      System.out.println(
          "REALM ************ saveStatus, ERROR guardando: \n " + orchextraStatus.toString());
      return new BusinessObject<>(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
        System.out.println(
            "REALM ************ saveStatus, SAVE OK : \n " + orchextraStatus.toString());
      }
    }
    System.out.println(
        "REALM ************ saveStatus,post finally: \n " + orchextraStatus.toString());
    return new BusinessObject<>(result, BusinessError.createOKInstance());
  }

  @Override public BusinessObject<OrchextraStatus> loadStatus() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    System.out.println("REALM ------------ loadStatus ");
    //fixme REALM
    SharedPreferences prefs =
        this.context.getSharedPreferences("com.gigigo.orchextraaa", Context.MODE_PRIVATE);
    String strBIsStatusInitialized = "com.gigigo.orchextra:bIsStatusInitialized";
    boolean bIsStatusInitialized = prefs.getBoolean(strBIsStatusInitialized, false);
    if (!bIsStatusInitialized) {
      System.out.println("REALM ########## OrchextraStatusDBDataSourceImpl creando status falso");
      OrchextraStatus resetStats = new OrchextraStatus();
      resetStats.setSession(null);
      resetStats.setStarted(false);
      resetStats.setInitialized(false);
      resetStats.setCrmUser(null);
      saveStatus(resetStats);

      SharedPreferences.Editor editor = prefs.edit();

      editor.putBoolean(strBIsStatusInitialized, true).commit();
      return new BusinessObject<>(resetStats, BusinessError.createOKInstance());
    } else {
      OrchextraStatus result = null;
      try {
        result = orchextraStatusReader.readStatus(realm);
        System.out.println("REALM ------------ loadStatus \n " + result.toString());
        //TODO retrieve crm and session
      } catch (NotFountRealmObjectException exception) {
        orchextraLogger.log(
            "REALM ------------  orchextraStatus info not present, new data will be created: "
                + exception.getMessage(), OrchextraSDKLogLevel.WARN);
        System.out.println("REALM ------------ loadStatus  NotFountRealmObjectException\n ");
        return new BusinessObject<>(OrchextraStatus.getInstance(),
            BusinessError.createOKInstance());
      } catch (RealmException re) {
        System.out.println("REALM ------------ loadStatus  RealmException\n ");
        return new BusinessObject<>(null, BusinessError.createKoInstance(re.getMessage()));
      } finally {
        if (realm != null) {
          realm.close();
          System.out.println("REALM ------------ loadStatus  realmClose\n ");
        }
      }
      System.out.println("REALM ------------ loadStatus  post finally \n ");
      return new BusinessObject<>(result, BusinessError.createOKInstance());
    }
  }
}
