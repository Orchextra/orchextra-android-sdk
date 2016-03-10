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
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.dataprovision.authentication.datasource.OrchextraStatusDBDataSource;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class OrchextraStatusDBDataSourceImpl implements OrchextraStatusDBDataSource {

  private final Context context;
  private final OrchextraStatusUpdater orchextraStatusUpdater;
  private final OrchextraStatusReader orchextraStatusReader;
  private final RealmDefaultInstance realmDefaultInstance;

  public OrchextraStatusDBDataSourceImpl(Context context, OrchextraStatusUpdater orchextraStatusUpdater,
      OrchextraStatusReader orchextraStatusReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.orchextraStatusUpdater = orchextraStatusUpdater;
    this.orchextraStatusReader = orchextraStatusReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  @Override public BusinessObject<OrchextraStatus> saveStatus(OrchextraStatus orchextraStatus) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    OrchextraStatus result = null;
    try {
      realm.beginTransaction();
      result = orchextraStatusUpdater.saveStatus(realm, orchextraStatus);
      //TODO Store crm and session
    } catch (RealmException re) {
      return new BusinessObject<>(null,BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
    return new BusinessObject<>(result, BusinessError.createOKInstance());
  }

  @Override public BusinessObject<OrchextraStatus> loadStatus() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    OrchextraStatus result = null;
    try {
      result = orchextraStatusReader.readStatus(realm);
      //TODO retrieve crm and session
    }catch (NotFountRealmObjectException exception){
      GGGLogImpl.log("orchextraStatus info not present, new data will be created: "
          +  exception.getMessage(), LogLevel.WARN);
      return new BusinessObject<>(OrchextraStatus.getInstance(),BusinessError.createOKInstance());
    } catch (RealmException re) {
      return new BusinessObject<>(null,BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
    return new BusinessObject<>(result, BusinessError.createOKInstance());
  }
}
