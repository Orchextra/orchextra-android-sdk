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

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import io.realm.Realm;
import io.realm.RealmObject;

public class OrchextraStatusUpdater {

  private final ModelToExternalClassMapper<OrchextraStatus, OrchextraStatusRealm> statusRealmMapper;

  public OrchextraStatusUpdater(
      ModelToExternalClassMapper<OrchextraStatus, OrchextraStatusRealm> statusRealmMapper) {
    this.statusRealmMapper = statusRealmMapper;
  }

  public OrchextraStatus saveStatus(Realm realm, OrchextraStatus orchextraStatus) {
    OrchextraStatusRealm realmItem = getRealmItem(realm, OrchextraStatusRealm.class);
    System.out.println("REALM ************ OrchextraStatusUpdater saveStatus ");
    OrchextraStatusRealm orchextraStatusRealm =
        statusRealmMapper.modelToExternalClass(orchextraStatus);

    System.out.println("REALM ************ OrchextraStatusUpdater saveStatus status:\n "
        + orchextraStatusRealm.toString());

    if (realmItem == null) {
      orchextraStatusRealm.setId(
          RealmDefaultInstance.getNextKey(realm, OrchextraStatusRealm.class));
      System.out.println("REALM ************ OrchextraStatusUpdater saveStatus realmitem null");
    } else {
      orchextraStatusRealm.setId(realmItem.getId());
      System.out.println("REALM ************ OrchextraStatusUpdater saveStatus realmitem!= null");
    }

    realm.copyToRealmOrUpdate(orchextraStatusRealm);

    return orchextraStatus;
  }

  private <T extends RealmObject> T getRealmItem(Realm realm, Class<T> classType) {
    return realm.where(classType).findFirst();
  }
}
